package dev.leandro.erllet.satella.service;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

/**
 * Serviço utilitário para utilização do arquivo de idiomas do Minecraft.
 *
 * @see
 * <a href="https://raw.githubusercontent.com/InventivetalentDev/minecraft-assets/1.14/assets/minecraft/lang/pt_br.json">Link para o arquivo de idiomas</a>
 */
public interface LanguageService {

    /**
     * Busca o texto no arquivo de idiomas a partir de uma chave.
     *
     * @param key  A chave identificando a mensagem no arquivo de idiomas. Ex: biome.minecraft.desert_lakes
     * @param args Parâmetros para formatar o texto do arquivo de idiomas, se necessário
     * @return Um Optional contendo o texto formatado, ou vazio caso a chave não exista
     */
    Optional<String> getMessage(String key, Object... args);

    /**
     * Monta o nome de um item com a quantidade, usando todos os recursos disponíveis, em ordem: <br>
     * - nome customizado {@link ItemMeta#getDisplayName()} <br>
     * - arquivo de idiomas <br>
     * - nome gerado a partir do material. ex: Material.DIAMOND_BLOCK  "1 Diamond Block"
     *
     * @param item O item cujo nome deve ser montado, caso seja {@code null}, será "Ar"
     * @return O nome item informado
     */
    default String getItemName(ItemStack item) {
        return getItemName(item, true);
    }

    /**
     * Monta o nome de um item, usando todos os recursos disponíveis, em ordem: <br>
     * - nome customizado {@link ItemMeta#getDisplayName()} <br>
     * - arquivo de idiomas <br>
     * - nome gerado a partir do material. ex: Material.DIAMOND_BLOCK  "1 Diamond Block"
     *
     * @param item       O item cujo nome deve ser montado, caso seja {@code null}, será "Ar"
     * @param showAmount Indica que o nome do item deve possuir a quantidade
     * @return O nome item informado
     */
    String getItemName(ItemStack item, boolean showAmount);

    /**
     * Monta o nome de um material usando todos os recursos disponíveis, em ordem: <br>
     * - arquivo de idiomas <br>
     * - nome gerado a partir do material. ex: Material.DIAMOND_BLOCK  "1 Diamond Block"
     *
     * @param material O tipo de material cujo nome deve ser montado, caso seja {@code null}, será "Ar"
     * @return O nome item informado
     */
    String getItemName(Material material);

}
