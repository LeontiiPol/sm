package ru.polovinko.state_machine.service;

// сервис для тестирование регинов, он должен содержать текущую стейт-машину

import lombok.Getter;
import lombok.Setter;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;

@Service
@Getter
@Setter
public class CurrentStateMachineHolder {

    private StateMachine<SmState, SmEvent> stateMachine;
    private StateMachine<SmState, SmEvent> innerStateMachine;
}
