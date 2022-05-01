package dev.leandro.erllet.satella.application.configuration;


import dev.leandro.erllet.satella.configuration.ModuleConfiguration;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ModuleConfigurationImpl implements ModuleConfiguration {

    @Delegate(types = ConfigurationSection.class)
    private final YamlConfiguration yamlConfiguration;

    private final File file;

    ModuleConfigurationImpl(YamlConfiguration yamlConfiguration, File file) {
        this.yamlConfiguration = yamlConfiguration;
        this.file = file;
    }

    @Override
    @SneakyThrows
    public void reload() {
        yamlConfiguration.load(file);
    }

    @Override
    @SneakyThrows
    public void save() {
        yamlConfiguration.save(file);
    }
}
