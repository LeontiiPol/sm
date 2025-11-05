package ru.polovinko.state_machine.listeners;

import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.*;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;

@WithStateMachine(id = "main")
@Log4j2
public class ContextIntegrator {

    @OnTransition(source = "INITIAL", target = "S1")
    public void onTransition(StateContext<SmState, SmEvent> context) {
        log.info("Логи из интегратора onTransition INITIAL -> S1");
    }

    @OnStateChanged(source = "INITIAL", target = "S1")
    public void onStateChanged() {
        log.info("Логи из интегратора onStateChanged INITIAL -> S1");
    }

    @OnEventNotAccepted
    public void anyEventNotAccepted() {
        log.info("Логи из интегратора anyEventNotAccepted");
    }

    @OnStateMachineStart
    public void onStateMachineStart() {
        log.info("Логи из интегратора onStateMachineStart");
    }

    @OnStateMachineStop
    public void onStateMachineStop() {
        log.info("Логи из интегратора onStateMachineStop");
    }

    @OnStateMachineError
    public void onStateMachineError() {
        log.info("Логи из интегратора onStateMachineError");
    }
}
