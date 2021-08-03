package io.github.badnotice.combatlog.manager;

import io.github.badnotice.combatlog.configuration.ConfigValue;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.World;

@Data
public final class PVPTimerManager {

    private World plotWorld;

    private boolean day;

    public void init() {
        this.plotWorld = Bukkit.getWorld(ConfigValue.get(ConfigValue::pvpTimerPlotWorld));
    }


}
