package dev.leandro.erllet.satella;

import dev.leandro.erllet.satella.service.ModuleService;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

@Setter
class ModuleRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> properties = annotationMetadata.getAnnotationAttributes(Module.class.getName());
        if (properties == null) return;
        ModuleService moduleService = beanFactory.getBean(ModuleService.class);
        moduleService.registerModule(properties.get("value").toString(), annotationMetadata.getClassName());
    }

}
