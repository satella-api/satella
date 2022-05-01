package dev.leandro.erllet.satella.application.configuration;


import dev.leandro.erllet.satella.configuration.ConfigurationService;
import dev.leandro.erllet.satella.configuration.ModuleConfiguration;
import dev.leandro.erllet.satella.service.ModuleService;
import lombok.val;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
class ConfigurationBeanProvider {

    @Primary
    @Bean(SCOPE_PROTOTYPE)
    ModuleConfiguration autoModuleConfig(ConfigurationService configurationService, ModuleService moduleService, InjectionPoint injectionPoint) {
        val module = moduleService.getTargetModule(injectionPoint);
        return module.map(s -> configurationService.getConfiguration(String.format("%s.yml", s))).orElse(null);
    }

}
