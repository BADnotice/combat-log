package io.github.badnotice.combatlog.task;

import io.github.badnotice.combatlog.CombatLogPlugin;
import io.github.badnotice.combatlog.configuration.ConfigValue;
import io.github.badnotice.combatlog.event.impl.CombatUnTagEvent;
import io.github.badnotice.combatlog.manager.CombatManager;
import io.github.badnotice.combatlog.util.Actionbar;
import io.github.badnotice.combatlog.util.StringUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author BADnotice
 */

@AllArgsConstructor
public final class CombatUpdateTask implements Runnable {

    private final CombatLogPlugin plugin;

    @Override
    public void run() {
        CombatManager combatManager = this.plugin.getCombatManager();

        combatManager.getAllCombats().forEach((s, combat) -> {
            Player player = Bukkit.getPlayer(s);

            if (!combat.expireAt()) {

                combatManager.removeCombat(combat);
                Player target = Bukkit.getPlayer(combat.getEnemy());

                if (player != null) {
                    String message = StringUtils.fixEspaces(ConfigValue.get(ConfigValue::messageInCombat)
                            .replace("{left_time}", String.valueOf(combat.getLeftTime()))
                            .replace("{enemy}", combat.getEnemy())
                            .replace("{faction_tag}", target == null ? "" : this.plugin.getFactionsHook().getTag(target))
                            .replace("{clan_tag}", target == null ? "" : this.plugin.getSimpleClanHook().getTag(target))
                            .replace("{prefix}", target == null ? "" : this.plugin.getChatHook().getPrefix(target))
                            .replace("{newline}", "\n"));

                    Actionbar.send(player, message);
                }
                return;
            }

            CombatUnTagEvent unTagEvent = new CombatUnTagEvent(combat);
            unTagEvent.call();

            if (player != null) {
                player.sendMessage(ConfigValue.get(ConfigValue::messageExpired));
            }
        });
    }

}
