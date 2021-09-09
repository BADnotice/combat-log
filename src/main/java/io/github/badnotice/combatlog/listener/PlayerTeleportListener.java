package io.github.badnotice.combatlog.listener;

import io.github.badnotice.combatlog.CombatLogPlugin;
import io.github.badnotice.combatlog.configuration.ConfigValue;
import io.github.badnotice.combatlog.configuration.LangValue;
import io.github.badnotice.combatlog.model.Combat;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

@AllArgsConstructor
public final class PlayerTeleportListener implements Listener {

    private final CombatLogPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();

        Combat combat = this.plugin.getCombatManager().findCombat(player.getName()).orElse(null);
        if (combat == null) {
            return;
        }

        checkPrevention(e);
    }

    private boolean isAllowed() {
        return !ConfigValue.get(ConfigValue::teleportionEnable);
    }

    private boolean isAllowed(PlayerTeleportEvent.TeleportCause teleportCause) {
        String teleportCauseName = teleportCause.name();
        return ConfigValue.get(ConfigValue::teleportionAllowdTelportCauseList).contains(teleportCauseName);
    }

    private void checkPrevention(PlayerTeleportEvent e) {
        if (e.isCancelled()) {
            return;
        }

        if (isAllowed()) {
            return;
        }

        PlayerTeleportEvent.TeleportCause teleportCause = e.getCause();
        if (isAllowed(teleportCause)) {
            return;
        }

        e.setCancelled(true);

        Player player = e.getPlayer();
        String messagePath = LangValue.get(LangValue::teleportationBlockOther);
        player.sendMessage(messagePath);
    }

}
