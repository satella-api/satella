package dev.leandro.erllet.satella.application.configuration;


import dev.leandro.erllet.satella.configuration.ConfigurationService;
import dev.leandro.erllet.satella.configuration.ModuleConfiguration;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private final Map<String, ModuleConfiguration> configurations = new HashMap<>();

    @Autowired
    private Plugin plugin;

    @Override
    public ModuleConfiguration getConfiguration(String filename) {
        return configurations.computeIfAbsent(filename, this::loadConfiguration);
    }

    @SneakyThrows
    private ModuleConfiguration loadConfiguration(String filename) {
        val configFile = new File(plugin.getDataFolder(), filename);
        if (!configFile.exists()) {
            plugin.saveResource(filename, false);
        }
        val yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        return new ModuleConfigurationImpl(yamlConfiguration, configFile);
    }
}
