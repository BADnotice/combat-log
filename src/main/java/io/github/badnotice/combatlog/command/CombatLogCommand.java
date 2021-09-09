package io.github.badnotice.combatlog.command;

import io.github.badnotice.combatlog.CombatLogPlugin;
import io.github.badnotice.combatlog.configuration.LangValue;
import io.github.badnotice.combatlog.registry.ConfigurationRegistry;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public final class CombatLogCommand implements CommandExecutor {

    private final CombatLogPlugin plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("combatlog.admin")) {
            sender.sendMessage(LangValue.get(LangValue::errorNoPermission));
            return true;
        }

        if (args.length != 1) {
            for (String message : LangValue.get(LangValue::errorHelpCommand)) {
                sender.sendMessage(message);
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            ConfigurationRegistry.of(plugin).init();

            sender.sendMessage("§aRecarregou com sucesso todos os arquivos de configuração.");
        }

        return false;
    }

}
