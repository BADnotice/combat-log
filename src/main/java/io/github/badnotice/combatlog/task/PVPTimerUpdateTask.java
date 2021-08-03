package io.github.badnotice.combatlog.task;

import io.github.badnotice.combatlog.configuration.LangValue;
import io.github.badnotice.combatlog.manager.PVPTimerManager;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public final class PVPTimerUpdateTask implements Runnable {

    private final PVPTimerManager pvpTimerManager;

    @Override
    public void run() {
        long worldTime = pvpTimerManager.getPlotWorld().getTime();

        if (worldTime < 13000 && !pvpTimerManager.isDay()) {
            pvpTimerManager.setDay(true);
            Bukkit.getLogger().info("é DIA CAMBADA");
            sendMessage(LangValue.get(LangValue::pvpTimerDisabled));
            return;
        }

        if (worldTime > 13000 && pvpTimerManager.isDay()) {
            pvpTimerManager.setDay(false);
            Bukkit.getLogger().info("é NOITE CAMBADA");
            sendMessage(LangValue.get(LangValue::pvpTimerActivated));
        }
    }

    private void sendMessage(List<String> messages) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            for (String message : messages) {
                onlinePlayer.sendMessage(message);
            }
        }
    }

}
