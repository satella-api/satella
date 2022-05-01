package dev.leandro.erllet.satella.service;

import org.springframework.beans.factory.InjectionPoint;

import java.util.Optional;
import java.util.Set;

/**
 * Serviço para gerenciar os módulos instalados
 */
public interface ModuleService {

    /**
     * Registra um módulo instalado
     *
     * @param name      O nome do módulo
     * @param className A classe principal do módulo
     */
    void registerModule(String name, String className);

    /**
     * Informa a classe principal de um módulo
     *
     * @param module O nome do módulo a ser consultado
     * @return O nome da classe principal (incluindo pacote) do módulo
     */
    String getModuleClassName(String module);

    /**
     * Tenta inferir o nome do módulo a partir de um ponto de injeção
     *
     * @param injectionPoint O ponto de injeção dentro de um módulo
     * @return Um optional contendo o nome do módulo, ou vazio caso o ponto de injeção não esteja em um módulo
     */
    Optional<String> getTargetModule(InjectionPoint injectionPoint);

    /**
     * @return O nome de todos os módulos instalados
     */
    Set<String> getLoadedModules();

}
