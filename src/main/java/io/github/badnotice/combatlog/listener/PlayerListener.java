package io.github.badnotice.combatlog.listener;

import io.github.badnotice.combatlog.CombatLogPlugin;
import io.github.badnotice.combatlog.api.CombatLogAPI;
import io.github.badnotice.combatlog.configuration.ConfigValue;
import io.github.badnotice.combatlog.enums.RestrictionType;
import io.github.badnotice.combatlog.event.impl.CombatPunishEvent;
import io.github.badnotice.combatlog.event.impl.CombatTagEvent;
import io.github.badnotice.combatlog.manager.CombatManager;
import io.github.badnotice.combatlog.model.Combat;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

@AllArgsConstructor
public final class PlayerListener implements Listener {

    private final CombatLogPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        Entity damager = event.getDamager();
        if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) && event.getDamager() instanceof Arrow) {

            Arrow arrow = (Arrow) damager;
            ProjectileSource shooter = arrow.getShooter();

            if (shooter instanceof Player) {
                CombatTagEvent tagEvent = new CombatTagEvent(
                        (Player) shooter,
                        (Player) entity
                );

                tagEvent.call();
            }

        }

        if (damager instanceof Player) {
            CombatTagEvent tagEvent = new CombatTagEvent(
                    (Player) damager,
                    (Player) entity
            );

            tagEvent.call();
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        Player entity = event.getEntity();
        Player killer = event.getEntity().getKiller();

        CombatManager combatManager = this.plugin.getCombatManager();
        Combat combat = combatManager.findCombat(entity.getName()).orElse(null);
        if (combat == null) return;

        combatManager.removeCombat(combat);
        if (killer != null) killer.sendMessage(ConfigValue.get(ConfigValue::messageDeadEnemy)
                .replace("{newline}", "\n")
                .replace("{enemy}", entity.getName())
        );
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase().split(" ")[0];

        Combat combat = this.plugin.getCombatManager().findCombat(event.getPlayer().getName()).orElse(null);
        if (combat == null) {
            return;
        }

        if (!ConfigValue.get(ConfigValue::commandEnable)) {
            return;
        }

        Player player = event.getPlayer();

        RestrictionType restrictionType = RestrictionType.valueOf(ConfigValue.get(ConfigValue::commandRestriction));
        switch (restrictionType) {
            case BLACKLIST:
                if (ConfigValue.get(ConfigValue::commandCommands).contains(command)) {
                    event.setCancelled(true);
                    player.sendMessage(ConfigValue.get(ConfigValue::commandMessage)
                            .replace("{newline}", "\n")
                    );
                }
                break;

            case WHITELIST:
                if (!ConfigValue.get(ConfigValue::commandCommands).contains(command)) {
                    event.setCancelled(true);
                    player.sendMessage(ConfigValue.get(ConfigValue::commandMessage)
                            .replace("{newline}", "\n")
                    );
                }
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Combat combat = this.plugin.getCombatManager().findCombat(player.getName()).orElse(null);
        if (combat == null || !ConfigValue.get(ConfigValue::punishEnable)) return;

        CombatPunishEvent punishEvent = new CombatPunishEvent(
                player,
                combat
        );

        punishEvent.call();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Player player = event.getPlayer();
            ItemStack item = event.getItem();

            if (item != null && item.getType() == Material.ENDER_PEARL && CombatLogAPI.getInstance().isInCombat(player.getName())) {
                event.setCancelled(true);
                player.sendMessage(ConfigValue.get(ConfigValue::messageUsageEnderpearl));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (CombatLogAPI.getInstance().isInCombat(player.getName())) {
            event.setCancelled(CombatLogAPI.getInstance().isInCombat(player.getName()));
            player.sendMessage(ConfigValue.get(ConfigValue::messageTeleport));
        }
    }

}
