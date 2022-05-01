package dev.leandro.erllet.satella.application.defaults;


import dev.leandro.erllet.satella.service.PermissionService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.Server;

import java.util.Set;

@AllArgsConstructor
class DefaultPermissionService implements PermissionService {

    private static final String MENSAGEM_ERRO = "Vault é necessário para esta operação";

    private final Server server;

    @Override
    public boolean hasPermission(String playerName, String permission) {
        val player = server.getPlayer(playerName);
        return player != null && player.hasPermission(permission);
    }

    @Override
    public void playerAdd(String player, String permission) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public void playerRemove(String player, String permission) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public void groupAdd(String group, String permission) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public void groupRemove(String group, String permission) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public boolean isPlayerInGroup(String player, String groupName) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public void addPlayerToGroup(String player, String groupName) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public void removePlayerFromGroup(String player, String groupName) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public Set<String> getPlayerGroups(String player) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public String getPrimaryGroup(String player) {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }

    @Override
    public Set<String> getGroups() {
        throw new UnsupportedOperationException(MENSAGEM_ERRO);
    }
}
