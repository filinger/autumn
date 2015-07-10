package com.technoirarts.autumn.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/2/2015
 * @since 1.0
 */
public class MapBeanRegistry implements BeanRegistry {

    private final HashMap<String, Bean<?>> beans;

    public MapBeanRegistry() {
        this.beans = new HashMap<>();
    }

    @Override
    public void register(Bean<?> bean) {
        String id = bean.getId();
        if (beans.containsKey(id)) {
            throw new IllegalStateException("there is already a registered bean with such id: " + id);
        }
        beans.put(id, bean);
    }

    @Override
    public Bean<?> findById(String beanId) {
        return beans.get(beanId);
    }

    @Override
    public Bean<?> findByType(String beanType) {
        try {
            return findByType(Class.forName(beanType));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public <T> Bean<T> findByType(Class<T> beanType) {
        Iterator<Bean<T>> beansOfType = findOfType(beanType).iterator();
        if (beansOfType.hasNext()) {
            return beansOfType.next();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ArrayList<Bean<T>> findOfType(Class<T> beanType) {
        ArrayList<Bean<T>> filtered = new ArrayList<>();
        for (Bean<?> bean : beans.values()) {
            if (bean.getType().isAssignableFrom(beanType)) {
                filtered.add((Bean<T>) bean);
            }
        }
        return filtered;
    }
}
