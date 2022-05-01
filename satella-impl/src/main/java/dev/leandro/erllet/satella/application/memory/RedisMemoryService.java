package dev.leandro.erllet.satella.application.memory;

import io.lettuce.core.RedisClient;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.NonNull;
import org.springframework.core.convert.ConversionService;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

class RedisMemoryService extends SerializedMemoryService implements Closeable {

    private final RedisClient client;

    private final StatefulRedisConnection<String, String> connection;

    private final RedisCommands<String, String> commands;

    RedisMemoryService(ConversionService conversionService, RedisClient client, StatefulRedisConnection<String, String> connection, RedisCommands<String,
            String> commands) {
        super(conversionService);
        this.client = client;
        this.connection = connection;
        this.commands = commands;
    }

    @Override
    String get(@NonNull String key) {
        return commands.get(key);
    }

    @Override
    public void remove(@NonNull String key) {
        commands.del(key);
    }

    @Override
    void set(@NonNull String key, String value, long expiration, TimeUnit expirationUnit) {
        commands.set(key, value, new SetArgs().ex(expirationUnit.toSeconds(expiration)));
    }

    @Override
    public boolean containsKey(String key) {
        return commands.exists(key) == 1;
    }

    @Override
    public void close() {
        connection.close();
        client.shutdown();
    }

}
