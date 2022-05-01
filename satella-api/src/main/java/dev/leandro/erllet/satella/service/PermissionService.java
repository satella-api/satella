package dev.leandro.erllet.satella.service;

import java.util.Set;

/**
 * Serviço de utilitários para a utilização do sistema de permissões do Vault de forma simplificada
 */
public interface PermissionService {

    /**
     * Verifica se um jogador tem uma permissão específica
     *
     * @param player O nome do jogador para ter a permissão verificada
     * @param permission A permissão a ser verificada
     * @return {@code true} se o jogador tiver permissão, {@code false} caso contrário
     */
    boolean hasPermission(String player, String permission);

    /**
     * Adiciona uma permissão na lista de permissões do jogador
     *
     * @param player O nome do jogador que deve receber a permissão
     * @param permission A permissão a ser concedida
     */
    void playerAdd(String player, String permission);

    /**
     * Remove uma permissão da lista de permissões do jogador
     *
     * @param player O nome do jogador que deve perder a permissão
     * @param permission A permissão a ser removida
     */
    void playerRemove(String player, String permission);

    /**
     * Adiciona uma permissão na lista de permissões do grupo
     *
     * @param group O nome do grupo que deve receber a permissão
     * @param permission A permissão a ser concedida
     */
    void groupAdd(String group, String permission);

    /**
     * Remove uma permissão da lista de permissões do grupo
     *
     * @param group O nome do grupo que deve perder a permissão
     * @param permission A permissão a ser removida
     */
    void groupRemove(String group, String permission);

    /**
     * Verifica se o determinado jogador está no grupo
     *
     * @param player O jogador a ser verificado
     * @param groupName O nome do grupo a ser verificado
     * @return {@code true} se o jogador estiver no grupo, {@code false} caso contrário
     */
    boolean isPlayerInGroup(String player, String groupName);

    /**
     * Adiciona o jogador em um determinado grupo
     *
     * @param player O jogador que deve entrar no grupo
     * @param groupName O nome do grupo que deve receber o jogador
     */
    void addPlayerToGroup(String player, String groupName);

    /**
     * Remove o jogador de um determinado grupo
     *
     * @param player O jogador que deve sair do grupo
     * @param groupName O nome do grupo que deve perder o jogador
     */
    void removePlayerFromGroup(String player, String groupName);

    /**
     * Retorna um conjunto com o nome de todos os grupos a qual o jogador pertence
     *
     * @param player O nome do jogador a ser consultado
     * @return Uma conjunto com o nome de todos os grupos do jogador
     */
    Set<String> getPlayerGroups(String player);

    /**
     * Retorna o nome do grupo principal do jogador
     *
     * @param player O nome do jogador a ser consultado
     * @return O nome do grupo principal do jogador
     */
    String getPrimaryGroup(String player);

    /**
     * Retorna um conjunto com os nomes de todos os grupos existentes no servidor
     *
     * @return Um conjunto com os nomes de todos os grupos existentes no servidor
     */
    Set<String> getGroups();

}
