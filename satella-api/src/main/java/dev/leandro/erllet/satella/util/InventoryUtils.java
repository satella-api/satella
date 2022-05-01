package dev.leandro.erllet.satella.util;

import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static dev.leandro.erllet.satella.util.ItemUtils.isEmpty;
import static java.util.Arrays.stream;

public interface InventoryUtils {

    /**
     * Informa se um inventário de tipo genérico está vazio
     *
     * @param inventory O inventário a ser verificado
     * @return {code true} se o inventário estiver vazio
     */
    static boolean isInventoryEmpty(Inventory inventory) {
        if (inventory == null) return true;
        return stream(inventory.getContents()).allMatch(ItemUtils::isEmpty);
    }

    /**
     * Informa se o inventário de um player está vazio, este método também verifica o slot da mão secundária e armadura
     *
     * @param inventory O inventário a ser verificado
     * @return {code true} se o inventário estiver vazio
     */
    static boolean isInventoryEmpty(PlayerInventory inventory) {
        if (inventory == null) return true;
        return isEmpty(inventory.getItemInMainHand())
                && isEmpty(inventory.getItemInOffHand())
                && isInventoryEmpty((Inventory) inventory)
                && stream(inventory.getArmorContents()).allMatch(ItemUtils::isEmpty);
    }

    /**
     * Limpa completamente o inventário de um player
     *
     * @param inventory O inventário a ser limpo
     */
    static void clearInventory(PlayerInventory inventory) {
        if (inventory == null) return;
        inventory.clear();
        inventory.setItemInOffHand(null);
        inventory.setHelmet(null);
        inventory.setChestplate(null);
        inventory.setLeggings(null);
        inventory.setBoots(null);
    }

    /**
     * Tenta dar o item ao player, mas caso o inventário esteja cheio, dropa na localização do player
     *
     * @param player O player que deve receber o item
     * @param item   O item que deve ser dado
     */
    static void safeAdd(Player player, ItemStack item) {
        if (ItemUtils.isEmpty(item)) return;
        val world = player.getWorld();
        val location = player.getLocation().clone();
        player.getInventory().addItem(item).values()
                .forEach(exceeded -> world.dropItemNaturally(location, exceeded));
    }

}
