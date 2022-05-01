package dev.leandro.erllet.satella.application.listener;


import dev.leandro.erllet.satella.event.PlayerPreDeathEvent;
import lombok.val;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class PreDeathListener implements Listener {

    @Autowired
    private Server server;

    @EventHandler(priority = EventPriority.HIGHEST)
    void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            val damage = event.getFinalDamage();
            if (player.getHealth() - damage <= 0) {
                server.getPluginManager().callEvent(new PlayerPreDeathEvent(player, event));
            }
        }
    }

}
