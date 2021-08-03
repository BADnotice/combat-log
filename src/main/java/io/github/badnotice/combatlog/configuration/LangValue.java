package io.github.badnotice.combatlog.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("lang.yml")
@TranslateColors
public final class LangValue implements ConfigurationInjectable {

    public static LangValue instance = new LangValue();

    // pvp-timer

    @ConfigField("pvp-timer.activated")
    private List<String> pvpTimerActivated;
    @ConfigField("pvp-timer.disabled")
    private List<String> pvpTimerDisabled;
    @ConfigField("pvp-timer.no-combat")
    private List<String> pvpTimerNoCombat;

    // command
    @ConfigField("command.block")
    private String commandBlock;

    // combat-timer

    @ConfigField("combat-timer.expire")
    private String combatTimerExpire;
    @ConfigField("combat-timer.enemy-death")
    private String combatTimerEnemyDeath;

    // teleportation

    @ConfigField("teleportation.block-pearl")
    private String teleportationBlockPearl;
    @ConfigField("teleportation.block-other")
    private String teleportationBlockOther;

    @ConfigField("in-combat")
    private String inCombat;
    @ConfigField("punish")
    private String punish;

    public static <T> T get(Function<LangValue, T> function) {
        return function.apply(instance);
    }

}
