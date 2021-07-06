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

    @ConfigField("staff-bypass") private ConfigurationSection staffBypass;

    @ConfigField("expire-time") private int expireTime;

    // punish

    @ConfigField("punish.enable") private boolean punishEnable;
    @ConfigField("punish.message") private String punishMessage;

    // commands-block

    @ConfigField("command.enable") private boolean commandEnable;
    @ConfigField("command.commands") private List<String> commandCommands;
    @ConfigField("command.message") private String commandMessage;
    @ConfigField("command.restriction") private String commandRestriction;

    // commands-block

    @ConfigField("worlds-block.enable") private boolean worldsBlockEnable;
    @ConfigField("worlds-block.list") private List<String> worldsBlockList;

    // messages

    @ConfigField("messages.dead-enemy") private String messageDeadEnemy;
    @ConfigField("messages.expired") private String messageExpired;
    @ConfigField("messages.in-combat") private String messageInCombat;
    @ConfigField("messages.usage-enderpearl") private String messageUsageEnderpearl;
    @ConfigField("messages.teleport") private String messageTeleport;

    public static <T> T get(Function<ConfigValue, T> function) {
        return function.apply(instance);
    }

}
