package dev.leandro.erllet.satella.application.defaults;


import dev.leandro.erllet.satella.service.PermissionService;
import org.bukkit.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class DefaultBeanProvider {

    @ConditionalOnMissingBean
    @Bean
    PermissionService defaultPermissionService(Server server) {
        return new DefaultPermissionService(server);
    }

}
