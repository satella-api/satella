package dev.leandro.erllet.satella.application;

import dev.leandro.erllet.satella.service.ModuleService;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Member;
import java.lang.reflect.Parameter;
import java.util.*;

@Log4j2
@Component
class ModuleServiceImpl implements ModuleService {

    private final Map<String, String> modules = new HashMap<>();

    private DataSource dataSource;

    private boolean initialized = false;

    @Override
    public void registerModule(String name, String className) {
        modules.put(name, className);
    }

    @Override
    public String getModuleClassName(String module) {
        return modules.get(module);
    }

    @Override
    public Optional<String> getTargetModule(InjectionPoint injectionPoint) {
        val annotatedElement = injectionPoint.getAnnotatedElement();
        Package pckg;
        if (annotatedElement instanceof Class) {
            pckg = ((Class) annotatedElement).getPackage();
        } else if (annotatedElement instanceof Parameter) {
            pckg = ((Parameter) annotatedElement).getDeclaringExecutable().getDeclaringClass().getPackage();
        } else if (annotatedElement instanceof Member) {
            pckg = ((Member) annotatedElement).getDeclaringClass().getPackage();
        } else {
            return Optional.empty();
        }
        return modules.entrySet().stream()
                .filter(entry -> pckg.getName().startsWith(entry.getValue().substring(0, entry.getValue().lastIndexOf("."))))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public Set<String> getLoadedModules() {
        return Collections.unmodifiableSet(modules.keySet());
    }

    @EventListener
    public void onStartup(ContextRefreshedEvent event) {
        if (initialized) return;
        initialized = true;
        try {
            initializeDependencies(event.getApplicationContext().getAutowireCapableBeanFactory());
            getLoadedModules().forEach(this::initializeModule);
        } catch (NoSuchBeanDefinitionException ignored) {
        }
    }

    private void initializeDependencies(BeanFactory beanFactory) {
        dataSource = beanFactory.getBean(DataSource.class);
    }

    private void initializeModule(String moduleName) {
        log.info("Initializing the module {}", moduleName);
        migrateDatabase(moduleName);
    }

    private void migrateDatabase(String moduleName) {
        Flyway flyway = new Flyway(dataSource.getClass().getClassLoader());

        flyway.setLocations("/sql_" + moduleName.toLowerCase());
        flyway.setTable("schema_history_" + moduleName.toLowerCase());
        flyway.setDataSource(dataSource);

        flyway.setBaselineOnMigrate(true);
        flyway.migrate();
    }
}
