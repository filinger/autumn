package com.technoirarts.autumn.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/7/2015
 * @since 1.0
 */
public class BeanRegistryValueResolver implements BeanValueResolver {

    private final BeanRegistry registry;

    public BeanRegistryValueResolver(BeanRegistry registry) {
        this.registry = registry;
    }

    public Object getValueById(String beanId) {
        Bean<?> bean = registry.findById(beanId);
        if (bean != null) {
            return bean.getValue();
        }
        return null;
    }

    public Object getValueByType(String beanType) {
        Bean<?> bean = registry.findByType(beanType);
        if (bean != null) {
            return bean.getValue();
        }
        return null;
    }

    public <T> T getValueByType(Class<T> beanType) {
        Bean<T> bean = registry.findByType(beanType);
        if (bean != null) {
            return bean.getValue();
        }
        return null;
    }

    @Override
    public <T> List<T> getValuesByType(Class<T> beanType) {
        List<Bean<T>> beans = registry.findOfType(beanType);
        ArrayList<T> values = new ArrayList<>(beans.size());
        for (Bean<T> bean : beans) {
            values.add(bean.getValue());
        }
        return values;
    }
}
