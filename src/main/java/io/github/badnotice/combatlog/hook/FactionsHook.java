package io.github.badnotice.combatlog.hook;

import com.massivecraft.factions.entity.MPlayer;
import io.github.badnotice.combatlog.CombatLogPlugin;
import org.bukkit.entity.Player;

/**
 * @author BADnotice
 */

public final class FactionsHook {

    private boolean enable = false;

    public void init(CombatLogPlugin plugin) {
        enable = plugin.getServer().getPluginManager().isPluginEnabled("Factions");
    }

    public String getTag(Player player) {
        if (!this.enable) {
            return "";
        }

        MPlayer mPlayer = MPlayer.get(player);
        return !mPlayer.hasFaction() ? "" : "§7[" + mPlayer.getFaction().getTag() + "]";
    }

}
