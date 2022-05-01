package dev.leandro.erllet.satella.application.memory;

import dev.leandro.erllet.satella.service.MemoryService;
import lombok.NonNull;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.util.concurrent.TimeUnit;

public abstract class SerializedMemoryService implements MemoryService {

    private final ConversionService conversionService;

    protected SerializedMemoryService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public final <T> T get(String key, Class<T> type) {
        val value = get(key);
        return value != null ? conversionService.convert(value, type) : null;
    }

    @Override
    public final <T> T get(String key, @NonNull TypeDescriptor type) {
        val value = get(key);
        return value != null ? (T) conversionService.convert(value, TypeDescriptor.valueOf(String.class), type) : null;
    }

    @Override
    public final void set(String key, @NonNull Object value, long expiration, TimeUnit expirationUnit) {
        if (!conversionService.canConvert(TypeDescriptor.valueOf(String.class), TypeDescriptor.forObject(value))) {
            throw new IllegalStateException("Não é possível armazenar um tipo sem conversor");
        }
        set(key, conversionService.convert(value, String.class), expiration, expirationUnit);
    }

    abstract String get(String key);

    abstract void set(String key, String value, long expiration, TimeUnit expirationUnit);
}
