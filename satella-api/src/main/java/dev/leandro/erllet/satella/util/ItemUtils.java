package dev.leandro.erllet.satella.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface ItemUtils {

    /**
     * Verifica se um determinado item não possui algum conteúdo, ou seja, é nulo ou tem o tipo "Ar"
     *
     * @param item O item a ser verificado
     * @return {@code true} caso o item não possua conteúdo
     */
    static boolean isEmpty(ItemStack item) {
        return item == null
                || Material.AIR.equals(item.getType())
                || "CAVE_AIR".equals(item.getType().name())
                || "VOID_AIR".equals(item.getType().name());
    }

}
