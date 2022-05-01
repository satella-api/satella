package dev.leandro.erllet.satella.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Getter
@Setter
@AllArgsConstructor
public class MenuItem {

    private ItemStack icon;

    private String name;

    private Consumer<InventoryClickEvent> action;
}
