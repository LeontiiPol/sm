package ru.polovinko.state_machine.listeners;

import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.monitor.AbstractStateMachineMonitor;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import reactor.core.publisher.Mono;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
public class SmMonitor extends AbstractStateMachineMonitor<SmState, SmEvent> {

    @Override
    public void transition(StateMachine<SmState, SmEvent> stateMachine,
                           Transition<SmState, SmEvent> transition,
                           long duration
    ) {
        super.transition(stateMachine, transition, duration);
        log.warn(
                "Переход из {} в {} занял {} мс.",
                Optional.ofNullable(transition.getSource()).map(State::getId).orElse(null),
                Optional.ofNullable(transition.getTarget()).map(State::getId).orElse(null),
                duration
        );
    }

    @Override
    public void action(StateMachine<SmState, SmEvent> stateMachine,
                       Function<StateContext<SmState, SmEvent>, Mono<Void>> action,
                       long duration
    ) {
        super.action(stateMachine, action, duration);
        log.warn(
                "action занял {} мс.",
                duration
        );
    }
}
