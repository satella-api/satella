package dev.leandro.erllet.satella;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indica a classe de configuração do módulo, cada módulo deve ter apenas uma.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ModuleRegistrar.class)
@ComponentScan
public @interface Module {

    /**
     * Determina o nome do módulo
     */
    String value();


}
