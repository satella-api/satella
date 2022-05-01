package dev.leandro.erllet.satella.application.memory;


import dev.leandro.erllet.satella.service.MemoryService;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.core.convert.TypeDescriptor;

import java.util.concurrent.TimeUnit;

class RuntimeMemoryService implements MemoryService {

    private final ExpiringMap<String, Object> map = ExpiringMap.builder().variableExpiration().build();

    @Override
    public <T> T get(String key, Class<T> type) {
        return type.cast(map.get(key));
    }

    @Override
    public <T> T get(String key, TypeDescriptor type) {
        return (T) type.getType().cast(map.get(key));
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public void set(String key, Object value, long expiration, TimeUnit expirationUnit) {
        map.put(key, value, expiration, expirationUnit);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

}
