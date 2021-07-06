package io.github.badnotice.combatlog.event.impl;

import io.github.badnotice.combatlog.event.EventWrapper;
import io.github.badnotice.combatlog.model.Combat;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

/**
 * @author BADnotice
 * Chamado quando o um combate é finalizado
 */

@Getter
@Setter
public final class CombatUnTagEvent extends EventWrapper implements Cancellable {

    private final Combat combat;

    private boolean cancelled;

    public CombatUnTagEvent(Combat combat) {
        this.combat = combat;
    }

}
