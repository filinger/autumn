package com.technoirarts.autumn.bean;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class BeanRegistryResolver implements BeanResolver {

    private final BeanRegistry registry;

    public BeanRegistryResolver(BeanRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Object resolve(EvaluationContext context, String beanName) throws AccessException {
        Object beanValue = getBeanValueById(beanName);
        if (beanValue != null) {
            return beanValue;
        } else {
            return getBeanValueByType(beanName);
        }
    }

    public Object getBeanValueById(String beanId) {
        Bean bean = registry.findById(beanId);
        if (bean != null) {
            return bean.getValue();
        }
        return null;
    }

    public Object getBeanValueByType(String beanType) {
        Bean bean = registry.findByType(beanType);
        if (bean != null) {
            return bean.getValue();
        }
        return null;
    }

    public <T> T getBeanValueByType(Class<T> beanType) {
        Bean<T> bean = registry.findByType(beanType);
        if (bean != null) {
            return bean.getValue();
        }
        return null;
    }
}
