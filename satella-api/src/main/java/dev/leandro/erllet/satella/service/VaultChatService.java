package dev.leandro.erllet.satella.service;

import org.bukkit.entity.Player;

/**
 * Serviço de utilitários para a utilização do sistema de permissões do Vault de forma simplificada
 */
public interface VaultChatService {

    /**
     * Verifica se um jogador tem uma permissão específica
     *
     * @param player O nome do jogador para ter a permissão verificada
     * @return {@code true} se o jogador tiver permissão, {@code false} caso contrário
     */
    String  getSuffix(Player player);

    /**
     * Adiciona uma permissão na lista de permissões do jogador
     *
     * @param player O nome do jogador que deve receber a permissão
     */
    String  getPrefix(Player player);

}
