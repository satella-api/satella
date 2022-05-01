package dev.leandro.erllet.satella.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;


public class CommandUtil {


    public static void unregisterCommands(List<String> commands) {
        commands.stream()
				.filter(s -> Bukkit.getServer().getPluginManager() instanceof SimplePluginManager)
                .forEach(CommandUtil::unregisterCommand);
    }

	@SneakyThrows
	private static void unregisterCommand(String command)  {
		val manager = (SimplePluginManager) Bukkit.getServer().getPluginManager();
		val commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
		commandMap.setAccessible(true);
		val map = (CommandMap) commandMap.get(manager);
		val knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
		knownCommandsField.setAccessible(true);
		val knownCommands = (Map<String, Command>) knownCommandsField.get(map);
		knownCommands
				.entrySet()
				.stream()
				.filter(stringCommandEntry -> stringCommandEntry.getKey().equals(command))
				.forEach(stringCommandEntry -> stringCommandEntry.getValue().unregister(map));
		knownCommands.remove(command);
	}

}

