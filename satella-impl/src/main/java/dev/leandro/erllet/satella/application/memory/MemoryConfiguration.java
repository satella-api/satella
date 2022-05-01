package dev.leandro.erllet.satella.application.memory;


import dev.leandro.erllet.satella.service.MemoryService;
import io.lettuce.core.RedisClient;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.bukkit.plugin.Plugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Log4j2
@Configuration
class MemoryConfiguration {

    @Bean
    @Scope(SCOPE_SINGLETON)
    MemoryService memoryService(Plugin plugin, ConversionService conversionService) {
        val redisUrl = plugin.getConfig().getString("redis-url");
        if (redisUrl != null) {
            try {
                val client = RedisClient.create(redisUrl);
                val connection = client.connect();
                val commands = connection.sync();
                return new RedisMemoryService(conversionService, client, connection, commands);
            } catch (Exception exception) {
                log.warn("Falha ao se conectar ao servidor redis", exception);
            }
        }

        try {
            Class.forName("org.h2.Driver");
            val h2File = new File(plugin.getDataFolder(), "memory").getAbsolutePath();
            val connection = DriverManager.getConnection("jdbc:h2:" + h2File);
            initTables(connection);
            purgeData(connection);
            return new H2MemoryService(conversionService, connection);
        } catch (Exception exception) {
            log.warn("Falha ao se conectar ao banco de dados h2 em memory.h2", exception);
        }

        log.warn("Utilizando memória em tempo de execução");
        return new RuntimeMemoryService();
    }

    private void initTables(Connection connection) throws SQLException {
        try (val statement = connection.createStatement()) {
            statement.executeUpdate("create table if not exists memory (key varchar(255) not null, value varchar(255) not null, " +
                    "expires_at bigint not null, primary key(key))");
        }
    }

    private void purgeData(Connection connection) throws SQLException {
        try (val statement = connection.prepareStatement("delete from memory where expires_at < ?")) {
            statement.setLong(1, System.currentTimeMillis());
            statement.executeUpdate();
        }
    }

}
