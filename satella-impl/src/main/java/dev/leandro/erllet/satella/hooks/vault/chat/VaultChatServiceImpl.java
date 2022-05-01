package dev.leandro.erllet.satella.hooks.vault.chat;

import dev.leandro.erllet.satella.service.VaultChatService;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
class VaultChatServiceImpl implements VaultChatService {

    @Autowired
    private Plugin plugin;

    private Chat chat;

    @PostConstruct
    void init() {
        RegisteredServiceProvider<Chat> rsp =
                plugin.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
    }

    public String getSuffix(Player player) {
        return chat.getPlayerSuffix(player);
    }

    public String getPrefix(Player player) {
        return chat.getPlayerPrefix(player);
    }
}
