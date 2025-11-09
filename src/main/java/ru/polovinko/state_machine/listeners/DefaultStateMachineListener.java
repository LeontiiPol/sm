package ru.polovinko.state_machine.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.AbstractState;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;
import ru.polovinko.state_machine.service.CurrentStateMachineHolder;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class DefaultStateMachineListener implements StateMachineListener<SmState, SmEvent> {

    private final CurrentStateMachineHolder stateMachineHolder;

    @Override
    public void stateChanged(State<SmState, SmEvent> from, State<SmState, SmEvent> to) {
        log.info(
                String.format(
                        ">>> Состояние изменилось с %s на %s%n",
                        from == null
                                ? "null"
                                : from.getId(),
                        to.getId()
                )
        );
    }

    @Override
    public void stateEntered(State<SmState, SmEvent> state) {
        log.info(">>> СМ вошла в новое состояние :: {}", state.getId());
    }

    @Override
    public void stateExited(State<SmState, SmEvent> state) {
        log.info(">>> СМ покинула старое состояние :: {}", state.getId());
    }

    @Override
    public void eventNotAccepted(Message<SmEvent> message) {
        log.warn(">>> Event неприемлем, пэйлод :: {}, хэдеры :: {}",
                message.getPayload(),
                message.getHeaders().toString()
        );
    }

    @Override
    public void transition(Transition<SmState, SmEvent> transition) {
        log.info(">>> Переход из :: {}, в :: {}",
                Optional.of(transition)
                        .map(Transition::getSource)
                        .map(State::getId)
                        .orElse(null),
                Optional.of(transition)
                        .map(Transition::getTarget)
                        .map(State::getId)
                        .orElse(null)
        );
    }

    @Override
    public void transitionStarted(Transition<SmState, SmEvent> transition) {
        log.info(">>> Начало перехода из :: {}, в :: {}",
                Optional.of(transition)
                        .map(Transition::getSource)
                        .map(State::getId)
                        .orElse(null),
                Optional.of(transition)
                        .map(Transition::getTarget)
                        .map(State::getId)
                        .orElse(null)
        );
    }

    @Override
    public void transitionEnded(Transition<SmState, SmEvent> transition) {
        log.info(">>> Конец перехода из :: {}, в :: {}",
                Optional.of(transition)
                        .map(Transition::getSource)
                        .map(State::getId)
                        .orElse(null),
                Optional.of(transition)
                        .map(Transition::getTarget)
                        .map(State::getId)
                        .orElse(null)
        );
    }

    @Override
    public void stateMachineStarted(StateMachine<SmState, SmEvent> stateMachine) {
        log.info(">>> Стейт-машина {} запустилась | состояние: {}", stateMachine.getUuid(), stateMachine.getState().getId());
        if (stateMachine.getState().getId().equals(SmState.S2)) {
            stateMachineHolder.setInnerStateMachine(((AbstractState<SmState, SmEvent>) stateMachine.getState()).getSubmachine());
            log.info("Вложенная СМ {}", stateMachineHolder.getInnerStateMachine().getUuid());
            return;
        }
        stateMachineHolder.setStateMachine(stateMachine);
    }

    @Override
    public void stateMachineStopped(StateMachine<SmState, SmEvent> stateMachine) {
        log.info(">>> Стейт-машина {} завершила работу", stateMachine.getUuid());
    }

    @Override
    public void stateMachineError(StateMachine<SmState, SmEvent> stateMachine, Exception e) {
        log.warn(">>> Ошибка во время работы стейт-машины с id :: {}, исключение :: {}",
                stateMachine.getUuid(),
                e.fillInStackTrace()
        );
    }

    @Override
    public void extendedStateChanged(Object o, Object o1) {
        log.info(">>> Проставление переменной по ключу {}, значение {}", o, o1);
    }

    @Override
    public void stateContext(StateContext<SmState, SmEvent> stateContext) {
    }
}
