package dev.leandro.erllet.satella.application;

import dev.leandro.erllet.satella.configuration.ModuleConfiguration;
import dev.leandro.erllet.satella.hooks.vault.VaultHookConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(VaultHookConfiguration.class)
@SpringBootApplication(scanBasePackages = {"dev.leandro.erllet.satella.application"})
public class SatellaApplication {

    @Autowired(required = false)
    private ModuleConfiguration moduleConfiguration;


}
