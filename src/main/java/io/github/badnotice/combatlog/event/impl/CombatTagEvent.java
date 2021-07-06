package io.github.badnotice.combatlog.event.impl;

import io.github.badnotice.combatlog.event.EventWrapper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author BADnotice
 * Chamado quando um combate é iniciado
 */

@Getter
@Setter
public final class CombatTagEvent extends EventWrapper implements Cancellable {

    private final Player damagee;
    private final Player damager;

    private boolean cancelled;

    public CombatTagEvent(Player damagee, Player damager) {
        this.damagee = damagee;
        this.damager = damager;
    }

}
