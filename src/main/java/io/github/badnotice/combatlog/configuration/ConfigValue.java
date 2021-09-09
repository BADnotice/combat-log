package io.github.badnotice.combatlog.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("config.yml")
@TranslateColors
public final class ConfigValue implements ConfigurationInjectable {

    public static ConfigValue instance = new ConfigValue();

    @ConfigField("staff-bypass")
    private ConfigurationSection staffBypass;

    @ConfigField("expire-time")
    private int expireTime;

    // punish

    @ConfigField("punish.enable")
    private boolean punishEnable;

    // commands-block

    @ConfigField("command.enable")
    private boolean commandEnable;
    @ConfigField("command.commands")
    private List<String> commandCommands;
    @ConfigField("command.restriction")
    private String commandRestriction;

    // commands-block

    @ConfigField("worlds-block.enable")
    private boolean worldsBlockEnable;
    @ConfigField("worlds-block.list")
    private List<String> worldsBlockList;

    @ConfigField("pvp-timer.plot-world")
    private String pvpTimerPlotWorld;
    @ConfigField("pvp-timer.enable")
    private boolean pvpTimerEnable;

    @ConfigField("teleportion.allowed-teleport-cause-list")
    private List<String> teleportionAllowdTelportCauseList;
    @ConfigField("teleportion.enable")
    private boolean teleportionEnable;

    public static <T> T get(Function<ConfigValue, T> function) {
        return function.apply(instance);
    }

}
