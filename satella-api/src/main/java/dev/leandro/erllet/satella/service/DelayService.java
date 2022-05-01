package dev.leandro.erllet.satella.service;

import dev.leandro.erllet.satella.exception.DelayHitException;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;

/**
 * Serviço utilitário para adicionar delay em sistemas
 */
public interface DelayService {

    /**
     * Realiza todas as verificações necessárias para ver se o {@code sender} pode continuar a operação
     *
     * @param sender    O jogador a ser testado
     * @param key       Um identificador para a operação
     * @param delay     A quantidade de tempo que o jogador deve esperar para realizar a operação novamente
     * @param delayUnit A unidade de tempo que o jogador deve esperar para realizar a operação novamente
     * @throws DelayHitException caso o jogador ainda não tenha ultrapassado o tempo
     *                                                           de delay
     */
    void assertDelay(CommandSender sender, String key, long delay, TimeUnit delayUnit);

    /**
     * Limpa o delay do jogador caso exista
     *
     * @param sender O nome do jogador a ter o delay limpo
     * @param key    O identificador do delay a ser limpo
     */
    void resetDelay(CommandSender sender, String key);

    /**
     * Verifica se o {@code sender} possui delay pendente
     *
     * @param sender O jogador a ser testado
     * @param key    Um identificador para a operação
     */
    boolean isInDelay(CommandSender sender, String key);
}
