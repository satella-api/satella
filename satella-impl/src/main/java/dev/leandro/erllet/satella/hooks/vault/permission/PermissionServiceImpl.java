package dev.leandro.erllet.satella.hooks.vault.permission;

import dev.leandro.erllet.satella.service.PermissionService;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
class PermissionServiceImpl implements PermissionService {

    @Autowired
    private Plugin plugin;

    private Permission permissions;

    @PostConstruct
    void init() {
        RegisteredServiceProvider<Permission> rsp =
                plugin.getServer().getServicesManager().getRegistration(Permission.class);
        permissions = rsp.getProvider();
    }

    @Override
    public boolean hasPermission(String player, String permission) {
        return permissions.playerHas((String) null, player, permission);
    }

    @Override
    public void playerAdd(String player, String permission) {
        permissions.playerAdd((String) null, player, permission);
    }

    @Override
    public void playerRemove(String player, String permission) {
        permissions.playerRemove((String) null, player, permission);
    }

    @Override
    public void groupAdd(String group, String permission) {
        permissions.groupAdd((String) null, group, permission);
    }

    @Override
    public void groupRemove(String group, String permission) {
        permissions.groupRemove((String) null, group, permission);
    }

    @Override
    public boolean isPlayerInGroup(String player, String groupName) {
        return permissions.playerInGroup((String) null, player, groupName);
    }

    @Override
    public void addPlayerToGroup(String player, String groupName) {
        permissions.playerAddGroup((String) null, player, groupName);
    }

    @Override
    public void removePlayerFromGroup(String player, String groupName) {
        permissions.playerRemoveGroup((String) null, player, groupName);
    }

    @Override
    public Set<String> getPlayerGroups(String player) {
        return new HashSet<>(Arrays.asList(permissions.getPlayerGroups((String) null, player)));
    }

    @Override
    public String getPrimaryGroup(String player) {
        return permissions.getPrimaryGroup((String) null, player);
    }

    @Override
    public Set<String> getGroups() {
        return new HashSet<>(Arrays.asList(permissions.getGroups()));
    }
}
