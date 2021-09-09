package io.github.badnotice.combatlog.listener;

import io.github.badnotice.combatlog.CombatLogPlugin;
import io.github.badnotice.combatlog.configuration.ConfigValue;
import io.github.badnotice.combatlog.event.impl.CombatPunishEvent;
import io.github.badnotice.combatlog.event.impl.CombatTagEvent;
import io.github.badnotice.combatlog.manager.CombatManager;
import io.github.badnotice.combatlog.model.Combat;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public final class CombatListener implements Listener {

    private final CombatLogPlugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCombatTag(CombatTagEvent event) {
        Player damager = event.getDamager();
        Player damagee = event.getDamagee();

        boolean isWorldBlocked = ConfigValue.get(ConfigValue::worldsBlockList).contains(damager.getWorld().getName());
        if (ConfigValue.get(ConfigValue::worldsBlockEnable) && !isWorldBlocked) {
            return;
        }

        ConfigurationSection staffBypassSection = ConfigValue.get(ConfigValue::staffBypass);
        if (staffBypassSection.getBoolean("enable")) {
            String permission = staffBypassSection.getString("permission");
            if (!damager.hasPermission(permission) || !damagee.hasPermission(permission)) {
                return;
            }
        }

        applyCombatTag(damager, damagee);
        applyCombatTag(damagee, damager);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCombatPunish(CombatPunishEvent event) {
        Player player = event.getPlayer();
        Combat combat = event.getCombat();

        player.setHealth(0.0D);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(event.getMessage()
                    .replace("{player}", player.getName())
                    .replace("{newline}", "\n")
            );
        }

        CombatManager combatManager = this.plugin.getCombatManager();
        combatManager.removeCombat(combat);
    }

    private void applyCombatTag(Player player1, Player player2) {
        CombatManager combatManager = this.plugin.getCombatManager();

        Combat combat = combatManager.findCombat(player1.getName()).orElse(null);
        if (combat != null) {
            combat.updateTime();
            return;
        }

        long toMillis = TimeUnit.SECONDS.toMillis(ConfigValue.get(ConfigValue::expireTime) + 1);
        combatManager.insertCombat(Combat.builder()
                .player(player1.getName())
                .enemy(player2.getName())
                .instant(Instant.now().plusMillis(toMillis))
                .build());

        player1.setFlying(false);
        player2.setFlying(false);

        player1.setAllowFlight(false);
        player2.setAllowFlight(false);
    }

}
