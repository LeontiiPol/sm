package ru.polovinko.state_machine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import ru.polovinko.state_machine.domain.Event;
import ru.polovinko.state_machine.domain.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener());

    }

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states.withStates()
                .initial(State.INITIAL)
                .states(EnumSet.allOf(State.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(State.INITIAL).target(State.S1).event(Event.E1)
                .and()
                .withExternal()
                .source(State.S1).target(State.S2).event(Event.E2);
    }

    @Bean
    public StateMachineListener<State, Event> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(org.springframework.statemachine.state.State<State, Event> from,
                                     org.springframework.statemachine.state.State<State, Event> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
