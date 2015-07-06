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

    private final HashMap<String, Bean> beanIdMap;
    private final ArrayList<Bean> beans;

    public MapBeanRegistry() {
        this.beanIdMap = new HashMap<>();
        this.beans = new ArrayList<>();
    }

    @Override
    public void register(Bean bean) {
        beanIdMap.put(bean.getId(), bean);
        beans.add(bean);
    }

    @Override
    public Bean findById(String id) {
        return beanIdMap.get(id);
    }

    @Override
    public Bean findByType(String className) {
        try {
            return findByType(Class.forName(className));
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
        for (Bean bean : beans) {
            if (bean.getType().isAssignableFrom(beanType)) {
                filtered.add((Bean<T>) bean);
            }
        }
        return filtered;
    }
}
