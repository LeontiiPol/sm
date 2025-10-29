package ru.polovinko.state_machine.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;
import ru.polovinko.state_machine.listeners.DefaultStateMachineListener;
import ru.polovinko.state_machine.service.CurrentStateMachineHolder;

import java.util.Random;

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
                .state(SmState.INITIAL)
                .state(SmState.S1)
                .state(SmState.S2)
                .and()
                .withStates()
                    .parent(SmState.S2)
                    .initial(SmState.S2I)
                    .state(SmState.S2I)
                    .state(SmState.S2F)
                    .and()
                .withStates()
                .state(SmState.S3)
                .end(SmState.S3);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<SmState, SmEvent> transitions) throws Exception {
        transitions.withExternal()
                    .source(SmState.INITIAL).target(SmState.S1).event(SmEvent.E1)
                .and()
                .withExternal()
                    .source(SmState.S1).target(SmState.S2).event(SmEvent.E2)
                .and()
                .withExternal()
                    .source(SmState.S2I).target(SmState.S2F).event(SmEvent.E3)
                .and()
                .withExternal()
                    .source(SmState.S2F).target(SmState.S3).event(SmEvent.E4);
    }

}
