package dev.leandro.erllet.satella.util;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface CollectionUtils {

    static <T, R> Collection<R> map(Collection<T> source, Function<T, R> mapperFunction) {
        return source.stream().map(mapperFunction).collect(Collectors.toList());
    }

    static <T, R> Set<R> mapSet(Collection<T> source, Function<T, R> mapperFunction) {
        return source.stream().map(mapperFunction).collect(Collectors.toSet());
    }

}
