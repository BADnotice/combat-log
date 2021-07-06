package io.github.badnotice.combatlog.event.impl;

import io.github.badnotice.combatlog.configuration.ConfigValue;
import io.github.badnotice.combatlog.event.EventWrapper;
import io.github.badnotice.combatlog.model.Combat;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author BADnotice
 * Chamado quando um jogador desloga em combate
 */

@Getter
@Setter
public final class CombatPunishEvent extends EventWrapper implements Cancellable {

    private final Player player;
    private final Combat combat;
    private String message = ConfigValue.get(ConfigValue::punishMessage);

    private boolean cancelled;

    public CombatPunishEvent(Player player, Combat combat) {
        this.player = player;
        this.combat = combat;
    }

}
