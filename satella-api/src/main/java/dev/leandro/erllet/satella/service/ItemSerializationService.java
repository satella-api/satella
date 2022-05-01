package dev.leandro.erllet.satella.service;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Stream;

/**
 * Serviço com utilitários para serializar e deserializar itens para armazenamento.
 */
public interface ItemSerializationService {

    /**
     * Deserializa itens previamente serializados
     *
     * @param serialized Os dados dos itens previamente serializados
     * @return Uma stream com todos os itens recriados a partir dos dados serializados
     */
    Stream<ItemStack> deSerialize(String serialized);


    /**
     * Deserializa item previamente serializado
     *
     * @param serialized Os dados do item previamente serializado
     * @return o item recriado a partir dos dados serializados
     */
    ItemStack deSerializeSingle(String serialized);

    /**
     * Serializa os itens em formato string sem nenhuma perda de dados
     *
     * @param items O array de itens a serem transformados
     * @return Uma string contendo todos os dados dos itens informados, ignorando valores nulos
     */
    String serialize(ItemStack[] items);

    /**
     * Serializa os itens em formato string sem nenhuma perda de dados
     *
     * @param items A lista de itens a serem transformados
     * @return Uma string contendo todos os dados dos itens informados, ignorando valores nulos
     */
    String serialize(List<ItemStack> items);

    /**
     * Serializa o item em formato string sem nenhuma perda de dados
     *
     * @param item O Item que será transformado
     * @return Uma string contendo todos os dados do item informado, ignorando valores nulos
     */
    String serialize(ItemStack item);

}
