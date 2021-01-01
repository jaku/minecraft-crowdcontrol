package io.github.lexikiq.crowdcontrol;

import lombok.Getter;

public enum ClassCooldowns {
    ENTITY(30),
    POTION(30),
    BLOCK(5),
    FALLING_BLOCK(30),
    WEATHER(30),
    TORCH(60)
    ;

    private final @Getter int seconds;
    ClassCooldowns(int seconds) {
        this.seconds = seconds;
    }
}
