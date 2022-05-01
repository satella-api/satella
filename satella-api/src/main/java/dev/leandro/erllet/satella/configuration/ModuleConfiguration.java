package dev.leandro.erllet.satella.configuration;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Representa a configuração de um módulo.
 * <p>
 * Caso injetado via {@link org.springframework.beans.factory.annotation.Autowired}, a configuração injetada será:
 * [nome do módulo].yml
 */
public interface ModuleConfiguration extends ConfigurationSection {

    /**
     * Recarrega todas as configurações do arquivo. Alterações não salvas são perdidas ao executar este método.
     */
    void reload();

    /**
     * Salva todas as alterações realizadas na configuração.
     */
    void save();

}
