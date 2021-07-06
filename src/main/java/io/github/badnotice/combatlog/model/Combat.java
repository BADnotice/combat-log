package io.github.badnotice.combatlog.model;

import io.github.badnotice.combatlog.configuration.ConfigValue;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * @author BADnotice
 */

@Builder
@Data
public final class Combat {

    private final String player;
    private final String enemy;

    private Instant instant;

    public void updateTime() {
        int expiretime = ConfigValue.get(ConfigValue::expireTime);
        instant = Instant.now().plusMillis(TimeUnit.SECONDS.toMillis(expiretime + 1));
    }

    public boolean expireAt() {
        return this.instant.isBefore(Instant.now());
    }

    public long getLeftTime() {
        return TimeUnit.MILLISECONDS.toSeconds(this.instant.toEpochMilli() - System.currentTimeMillis());
    }

}