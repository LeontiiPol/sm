package ru.polovinko.state_machine.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.action.Actions;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;
import ru.polovinko.state_machine.listeners.DefaultStateMachineListener;
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
                .listener(new DefaultStateMachineListener(stateMachineHolder));

    }

    @Override
    public void configure(StateMachineStateConfigurer<SmState, SmEvent> states) throws Exception {
        states.withStates()
                .initial(SmState.INITIAL)
                .fork(SmState.S2)
                .join(SmState.S3)
                .states(EnumSet.allOf(SmState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<SmState, SmEvent> transitions) throws Exception {
        transitions.withExternal()
                    .source(SmState.INITIAL).target(SmState.S1).event(SmEvent.E1)
                .and()
                .withExternal()
                    .source(SmState.S1).target(SmState.S2).event(SmEvent.E2)
                .and()
                .withFork()
                    .source(SmState.S2)
                    .target(SmState.S2I)
                    .target(SmState.S2F)
                .and()
                .withJoin()
                    .source(SmState.S2I)
                    .source(SmState.S2F)
                    .target(SmState.S3);
    }

    @Bean
    public Action<SmState, SmEvent> myAction() {
        return context -> {
            throw new RuntimeException("MyError");
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
}
