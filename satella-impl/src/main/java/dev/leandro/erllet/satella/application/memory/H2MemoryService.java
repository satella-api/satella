package dev.leandro.erllet.satella.application.memory;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.core.convert.ConversionService;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

class H2MemoryService extends SerializedMemoryService implements Closeable {

    private final Connection connection;

    private PreparedStatement readStatement, containsStatement, deleteStatement, insertStatement;

    @SneakyThrows
    H2MemoryService(ConversionService conversionService, Connection connection) {
        super(conversionService);
        this.connection = connection;
        this.createStatements();
    }

    @SneakyThrows
    private void createStatements() {
        this.readStatement = connection.prepareStatement("select value from memory where key = ? and expires_at >= ?");
        this.containsStatement = connection.prepareStatement("select 1 from memory where key = ? and expires_at >= ?");
        this.deleteStatement = connection.prepareStatement("delete from memory where key = ?");
        this.insertStatement = connection.prepareStatement("insert into memory values (?, ?, ?)");
    }

    @Override
    @SneakyThrows
    String get(String key) {
        readStatement.clearParameters();
        readStatement.setString(1, key);
        readStatement.setLong(2, System.currentTimeMillis());
        try (val resultSet = readStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString("value");
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public void remove(String key) {
        deleteStatement.clearParameters();
        deleteStatement.setString(1, key);
        deleteStatement.executeUpdate();
    }

    @Override
    @SneakyThrows
    void set(@NonNull String key, @NonNull String value, long expiration, @NonNull TimeUnit expirationUnit) {
        val now = System.currentTimeMillis();
        remove(key);
        insertStatement.clearParameters();
        insertStatement.setString(1, key);
        insertStatement.setString(2, value);
        insertStatement.setLong(3, now + expirationUnit.toMillis(expiration));
        insertStatement.executeUpdate();
    }

    @Override
    @SneakyThrows
    public boolean containsKey(String key) {
        containsStatement.clearParameters();
        containsStatement.setString(1, key);
        containsStatement.setLong(2, System.currentTimeMillis());
        try (val resultSet = containsStatement.executeQuery()) {
            return resultSet.next();
        }
    }

    @Override
    @SneakyThrows
    public void close() {
        readStatement.close();
        insertStatement.close();
        containsStatement.close();
        deleteStatement.close();
        connection.close();
    }
}
