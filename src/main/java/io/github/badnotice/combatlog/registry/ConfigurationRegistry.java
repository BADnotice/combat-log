package io.github.badnotice.combatlog.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import io.github.badnotice.combatlog.configuration.ConfigValue;
import lombok.Data;
import org.bukkit.plugin.java.JavaPlugin;

@Data(staticConstructor = "of")
public final class ConfigurationRegistry {

    private final JavaPlugin plugin;

    public void init() {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);

        configurationInjector.saveDefaultConfiguration(
                plugin,
                "config.yml"
        );

        configurationInjector.injectConfiguration(
                ConfigValue.instance
        );

    }

}