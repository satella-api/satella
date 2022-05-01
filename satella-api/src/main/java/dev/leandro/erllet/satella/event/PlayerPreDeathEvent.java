package dev.leandro.erllet.satella.event;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerEvent;

/**
 * Evento disparado no último dano antes do jogador morrer, útil para executar ações antes que o jogador veja a tela
 * de respawn.
 */
public class PlayerPreDeathEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final EntityDamageEvent cause;

    public PlayerPreDeathEvent(@NonNull Player player, @NonNull EntityDamageEvent cause) {
        super(player);
        this.cause = cause;
    }

    /**
     * @return O evento que foi disparado antes do player morrer
     */
    public EntityDamageEvent getCause() {
        return cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cause.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        cause.setCancelled(cancel);
    }
}
