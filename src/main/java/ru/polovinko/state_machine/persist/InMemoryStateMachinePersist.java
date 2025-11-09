package ru.polovinko.state_machine.persist;

import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import ru.polovinko.state_machine.domain.ContextObject;
import ru.polovinko.state_machine.domain.SmEvent;
import ru.polovinko.state_machine.domain.SmState;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class InMemoryStateMachinePersist implements StateMachinePersist<SmState, SmEvent, ContextObject> {

    private final Map<UUID, StateMachineContext<SmState, SmEvent>> cache = new ConcurrentHashMap<>();

    @Override
    public void write(StateMachineContext<SmState, SmEvent> context, ContextObject contextObj) throws Exception {
        cache.merge(
                contextObj.getUuid(),
                context,
                (a, b) -> {
                    log.warn("В кэше уже есть этот контекст, обновлям его");
                    return b;
                }
        );
    }

    @Override
    public StateMachineContext<SmState, SmEvent> read(ContextObject contextObj) throws Exception {
        return cache.get(contextObj.getUuid());
    }
}
