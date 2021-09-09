package io.github.badnotice.combatlog.hook;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author BADnotice
 */

public final class ChatHook {

    private Chat chat;

    public void init() {
        RegisteredServiceProvider<Chat> registration = Bukkit.getServicesManager().getRegistration(Chat.class);
        if (registration != null) chat = registration.getProvider();
    }

    public String getPrefix(Player player) {
        return ChatColor.translateAlternateColorCodes('&', chat.getPlayerPrefix(player));
    }

    public String getSuffix(Player player) {
        return ChatColor.translateAlternateColorCodes('&', chat.getPlayerSuffix(player));
    }

}
