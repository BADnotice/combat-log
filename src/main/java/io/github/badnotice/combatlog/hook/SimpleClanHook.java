package io.github.badnotice.combatlog.hook;

import io.github.badnotice.combatlog.CombatLogPlugin;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * @author BADnotice
 */

public final class SimpleClanHook {

    private SimpleClans simpleClans;
    private boolean enable = false;

    public void init(CombatLogPlugin plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        enable = pluginManager.isPluginEnabled("SimpleClans");
        if (enable) {
            Plugin instance = pluginManager.getPlugin("SimpleClans");
            if (instance != null) simpleClans = ((SimpleClans) instance);
        }
    }

    public String getTag(Player player) {
        if (!this.enable) {
            return "";
        }

        ClanPlayer clanPlayer = simpleClans.getClanManager().getClanPlayer(player);
        return clanPlayer == null ? "" : "§7[" + clanPlayer.getTag() + "]";
    }

}
