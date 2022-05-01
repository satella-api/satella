package dev.leandro.erllet.satella.application;

import dev.leandro.erllet.satella.application.loader.ModuleLoader;
import dev.leandro.erllet.satella.util.CommandUtil;
import dev.alangomes.springspigot.SpringSpigotBootstrapper;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


@Log4j2
public class SatellaPlugin extends JavaPlugin {

    private ConfigurableApplicationContext context;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        boolean shutdownOnError = getConfig().getBoolean("spring.shutdown-on-error", true);
        try {
            loadDataSourceDriver();
            initializeSpring();
        } catch (Throwable t) {
            log.error("Failed to initialize satella", t);
            selfDisable();
            if (shutdownOnError) {
                getServer().shutdown();
            }
        }
    }

    @SneakyThrows
    private void loadDataSourceDriver() {
        String driverClassName = getConfig().getString("spring.datasource.driver-class-name");
        if (driverClassName != null) {
            Class.forName(driverClassName, true, getClassLoader());
        }
    }

    private Collection<Class<?>> loadModules() {
        val moduleLoader = new ModuleLoader(this.getClassLoader());
        val moduleFolder = new File(getDataFolder(), "modules");
        moduleFolder.mkdirs();
        return moduleLoader.loadModules(moduleFolder);
    }

    @SneakyThrows
    private void initializeSpring() {
        val unregisterCommands = Optional
                .of(getConfig().getStringList("unregister-commands"))
                .orElse(new ArrayList<>());
        CommandUtil.unregisterCommands(unregisterCommands);
        val modules = loadModules();
        val serverExecutor = Optional
                .ofNullable(getConfig().getString("server.executor"))
                .orElse("Paper");
        if (modules.isEmpty()) {
            log.warn("No modules found, turning off satella...");
            selfDisable();
            return;
        }
        context = SpringSpigotBootstrapper.initialize(serverExecutor, this,
                new SpringApplicationBuilder(SatellaApplication.class)
                        .sources(modules.toArray(new Class<?>[0]))
        );
    }

    private void selfDisable() {
        try {
            getServer().getPluginManager().disablePlugin(this);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onDisable() {
        try {
            if (context != null) {
                context.close();
            }
        } finally {
            context = null;
        }
    }

}
