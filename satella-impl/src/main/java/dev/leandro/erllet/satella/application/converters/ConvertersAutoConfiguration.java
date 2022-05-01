package dev.leandro.erllet.satella.application.converters;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
class ConvertersAutoConfiguration {

    @Autowired
    private DefaultConversionService conversionService;

    @Autowired
    private Server server;

    @PostConstruct
    void init() {
        conversionService.addConverter(ItemStack.class, String.class, (source) -> {
            try {
                return StreamSerializer.getDefault().serializeItemStack(source);
            } catch (IOException e) {
                return null;
            }
        });
        conversionService.addConverter(String.class, ItemStack.class, (source) -> {
            try {
                return StreamSerializer.getDefault().deserializeItemStack(source);
            } catch (IOException e) {
                return null;
            }
        });

        conversionService.addConverter(String.class, Player.class, server::getPlayer);
        conversionService.addConverter(Player.class, String.class, Player::getName);

        conversionService.addConverter(String.class, World.class, server::getWorld);
        conversionService.addConverter(World.class, String.class, World::getName);
    }

}
