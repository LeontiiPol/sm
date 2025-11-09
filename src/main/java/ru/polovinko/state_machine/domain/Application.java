package ru.polovinko.state_machine.domain;

import java.util.UUID;

public class Application implements ContextObject {

    private final UUID uuid = UUID.randomUUID();

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
