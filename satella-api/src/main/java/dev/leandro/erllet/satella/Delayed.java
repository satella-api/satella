package dev.leandro.erllet.satella;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
import dev.leandro.erllet.satella.exception.DelayHitException;

/**
 * Indica que o método possui um delay de utilização
 * Lança {@link DelayHitException} caso o jogador ainda não tenha ultrapassado o tempo de delay
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Delayed {

    /**
     * A quantidade de tempo que o jogador deve esperar para realizar a operação novamente
     */
    long delay();

    /**
     * A unidade de tempo que o jogador deve esperar para realizar a operação novamente
     */
    TimeUnit unit();

}
