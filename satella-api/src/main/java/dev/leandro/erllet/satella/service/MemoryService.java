package dev.leandro.erllet.satella.service;

import org.springframework.core.convert.TypeDescriptor;

import java.util.concurrent.TimeUnit;

/**
 * Serviço utilitário para armazenar dados por tempo determinado
 */
public interface MemoryService {

    /**
     * Busca um valor no armazenamento pela chave
     *
     * @param key A chave a ser buscada no armazenamento
     * @param type O tipo do valor para realizar a conversão
     * @return O valor da chave armazenada
     */
    <T> T get(String key, Class<T> type);

    /**
     * Busca um valor no armazenamento pela chave
     *
     * @param key A chave a ser buscada no armazenamento
     * @param type O tipo do valor para realizar a conversão
     * @return O valor da chave armazenada
     */
    <T> T get(String key, TypeDescriptor type);

    /**
     * Remove um valor do armazenamento
     *
     * @param key O valor a ser removido do armazenamento
     */
    void remove(String key);

    /**
     * Adiciona um valor identificado por uma chave no armazenamento
     *
     * @param key A chave que irá identificar o valor no armazenamento
     * @param value O valor a ser armazenado
     * @param expiration A quantidade de tempo que o valor deve ser mantido armazenado
     * @param expirationUnit A unidade de tempo que o valor deve ser mantido armazenado
     */
    void set(String key, Object value, long expiration, TimeUnit expirationUnit);

    /**
     * Verifica se existe um valor para a determinada chave no armazenamento
     *
     * @param key A chave a ser consultada
     * @return {@code true} caso a chave exista no armazenamento
     */
    boolean containsKey(String key);

}
