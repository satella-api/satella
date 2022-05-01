package dev.leandro.erllet.satella.hooks.vault;

import net.milkbowl.vault.permission.Permission;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@ConditionalOnClass(Permission.class)
@ConditionalOnProperty(prefix = "mcspring.vault.economy", name = "enabled", matchIfMissing = true)
public class VaultHookConfiguration {
}
