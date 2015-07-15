package com.technoirarts.autumn.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/2/2015
 * @since 1.0
 */
public class MapBeanRegistry implements BeanRegistry {

    private final HashMap<String, Object> beans;

    public MapBeanRegistry() {
        this.beans = new HashMap<>();
    }

    @Override
    public void register(String beanId, Object bean) {
        if (beans.containsKey(beanId)) {
            throw new IllegalStateException("there is already a registered bean with such id: " + beanId);
        }
        beans.put(beanId, bean);
    }

    @Override
    public Object findById(String beanId) {
        return beans.get(beanId);
    }

    @Override
    public <T> T findByType(Class<T> beanType) {
        Iterator<T> beansOfType = findOfType(beanType).iterator();
        if (beansOfType.hasNext()) {
            return beansOfType.next();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> findOfType(Class<T> beanType) {
        List<T> filtered = new ArrayList<>();
        for (Object bean : beans.values()) {
            if (beanType.isAssignableFrom(bean.getClass())) {
                filtered.add((T) bean);
            }
        }
        return filtered;
    }
}
