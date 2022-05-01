package dev.leandro.erllet.satella.application.loader;

import dev.leandro.erllet.satella.Module;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@AllArgsConstructor
public class ModuleLoader {

    private final ClassLoader classLoader;

    public Collection<Class<?>> loadModules(File folder) {
        Arrays.stream(defaultIfNull(folder.listFiles(), new File[0]))
                .filter(file -> file.getName().endsWith(".jar"))
                .forEach(this::loadModule);
        val reflections = new Reflections(new ConfigurationBuilder()
                .addClassLoaders(classLoader)
                .setUrls(ClasspathHelper.forPackage("dev.leandro.erllet"))
                .filterInputsBy(new FilterBuilder().includePackage("dev.leandro.erllet.satella"))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));
        return reflections.getTypesAnnotatedWith(Module.class);
    }

    @SneakyThrows
    private void loadModule(File file) {
        val jarUrl = file.toURI().toURL();
        val method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(classLoader, jarUrl);
    }

}
