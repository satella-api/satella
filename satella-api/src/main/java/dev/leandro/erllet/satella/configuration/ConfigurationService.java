package dev.leandro.erllet.satella.configuration;

/**
 * Serviço para obter configurações embutidas nos módulos, raramente deve ser usado, exceto para carregamento manual
 * de configuração.
 *
 * @see ModuleConfiguration
 */
public interface ConfigurationService {

    /**
     * Busca a configuração com um determinado nome, caso ela não exista na pasta do plugin, ela
     * será criada
     *
     * @param filename O nome do arquivo de configuração. Ex: chat.yml
     * @return A configuração do módulo
     */
    ModuleConfiguration getConfiguration(String filename);

}
