package ru.polovinko.state_machine.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.monitor.StateMachineMonitor;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import ru.polovinko.state_machine.domain.ContextObject;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;
import ru.polovinko.state_machine.listeners.DefaultStateMachineListener;
import ru.polovinko.state_machine.listeners.SmMonitor;
import ru.polovinko.state_machine.persist.InMemoryStateMachinePersist;
import ru.polovinko.state_machine.service.CurrentStateMachineHolder;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
@Log4j2
@RequiredArgsConstructor
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<SmState, SmEvent> {

    private final CurrentStateMachineHolder stateMachineHolder;

    @Override
    public void configure(StateMachineConfigurationConfigurer<SmState, SmEvent> config) throws Exception {
        System.out.println("Конфигурация стейт-машины");
        config.withConfiguration()
                .autoStartup(true)
                .machineId("main")
                .listener(new DefaultStateMachineListener(stateMachineHolder))
                .and()
                    .withMonitoring().monitor(stateMachineMonitor());
    }

    @Override
    public void configure(StateMachineStateConfigurer<SmState, SmEvent> states) throws Exception {
        states.withStates()
                .initial(SmState.INITIAL)
                .state(SmState.INITIAL)
                .state(SmState.S1)
                .state(SmState.S2)
                .state(SmState.S3)
                .end(SmState.S4);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<SmState, SmEvent> transitions) throws Exception {
        transitions.withExternal()
                    .source(SmState.INITIAL).target(SmState.S1).event(SmEvent.E1)
                .and()
                .withExternal()
                    .source(SmState.S1)
                    .target(SmState.S2)
                    .event(SmEvent.E2)
                    .action(myAction())
                    .action(mySlowAction())
                .and()
                .withExternal()
                    .source(SmState.S2).target(SmState.S3).event(SmEvent.E3)
                .and()
                .withExternal()
                    .source(SmState.S3).target(SmState.S4).event(SmEvent.E4);
    }

    @Bean
    public Action<SmState, SmEvent> myAction() {
        return context -> {
            System.out.println("myAction works");
            System.out.println("myAction finished");
        };
    }

    @Bean
    public Action<SmState, SmEvent> mySlowAction() {
        return context -> {
            System.out.println("mySlowAction works");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("mySlowAction finished");
        };
    }

    @Bean
    public Action<SmState, SmEvent> errorAction() {
        return context -> {
            Exception exception = context.getException();
            log.error("Возникла ошибка", exception);
        };
    }

    @Bean
    public Guard<SmState, SmEvent> guard() {
        return context -> false;
    }

    @Bean
    public StateMachinePersister<SmState, SmEvent, ContextObject> smPersister() {
        return new DefaultStateMachinePersister<>(new InMemoryStateMachinePersist());
    }

    @Bean
    public StateMachineMonitor<SmState, SmEvent> stateMachineMonitor() {
        return new SmMonitor();
    }
}
