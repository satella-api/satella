package dev.leandro.erllet.satella.application.memory;


import dev.leandro.erllet.satella.Delayed;
import dev.leandro.erllet.satella.service.DelayService;
import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.util.AopAnnotationUtils;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Aspect
@Component
@Scope(SCOPE_SINGLETON)
class DelayAspect {

    @Autowired
    private Context context;

    @Autowired
    private DelayService delayService;

    @Order(0)
    @Around("within(@(@dev.leandro.erllet.satella.Delayed *) *) " +
            "|| execution(@(@dev.leandro.erllet.satella.Delayed *) * *(..)) " +
            "|| @within(dev.leandro.erllet.satella.Delayed)" +
            "|| execution(@dev.leandro.erllet.satella.Delayed * *(..))")
    public Object checkDelay(ProceedingJoinPoint joinPoint) throws Throwable {
        val sender = context.getSender();
        if (sender == null) {
            return joinPoint.proceed();
        }
        val method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        val key = method.getDeclaringClass().getName() + "#" + method.getName();
        AopAnnotationUtils.getAppliableAnnotations(method, Delayed.class)
                .forEach(delayed -> delayService.assertDelay(sender, key, delayed.delay(), delayed.unit()));
        return joinPoint.proceed();
    }

}
