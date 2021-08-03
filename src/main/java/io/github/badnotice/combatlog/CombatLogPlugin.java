package io.github.badnotice.combatlog;

import io.github.badnotice.combatlog.command.CombatLogCommand;
import io.github.badnotice.combatlog.configuration.ConfigValue;
import io.github.badnotice.combatlog.hook.ChatHook;
import io.github.badnotice.combatlog.hook.FactionsHook;
import io.github.badnotice.combatlog.hook.SimpleClanHook;
import io.github.badnotice.combatlog.listener.CombatListener;
import io.github.badnotice.combatlog.listener.PlayerListener;
import io.github.badnotice.combatlog.manager.CombatManager;
import io.github.badnotice.combatlog.manager.PVPTimerManager;
import io.github.badnotice.combatlog.registry.ConfigurationRegistry;
import io.github.badnotice.combatlog.task.CombatUpdateTask;
import io.github.badnotice.combatlog.task.PVPTimerUpdateTask;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CombatLogPlugin extends JavaPlugin {

    private CombatManager combatManager;
    private PVPTimerManager pvpTimerManager;

    private FactionsHook factionsHook;
    private SimpleClanHook simpleClanHook;
    private ChatHook chatHook;

    public static CombatLogPlugin getInstance() {
        return getPlugin(CombatLogPlugin.class);
    }

    @Override
    public void onEnable() {
        ConfigurationRegistry.of(this).init();

        combatManager = new CombatManager();

        factionsHook = new FactionsHook();
        factionsHook.init(this);

        chatHook = new ChatHook();
        chatHook.init();

        simpleClanHook = new SimpleClanHook();
        simpleClanHook.init(this);

        this.registerListeners(
                new PlayerListener(this),
                new CombatListener(this)
        );

        this.getServer().getScheduler().runTaskTimerAsynchronously(
                this,
                new CombatUpdateTask(this),
                0,
                20
        );

        pvpTimerManager = new PVPTimerManager();

        if (ConfigValue.get(ConfigValue::pvpTimerEnable)) {
            pvpTimerManager.init();

            this.getServer().getScheduler().runTaskTimerAsynchronously(
                    this,
                    new PVPTimerUpdateTask(pvpTimerManager),
                    0,
                    20
            );
        }

        getCommand("combatlog").setExecutor(new CombatLogCommand(this));

        int pluginId = 11906;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new Metrics.SimplePie(
                "combats",
                () -> String.valueOf(this.getCombatManager().getAllCombats().size()))
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    protected void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

}
