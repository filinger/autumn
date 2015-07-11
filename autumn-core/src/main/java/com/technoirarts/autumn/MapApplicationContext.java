package com.technoirarts.autumn;

import com.technoirarts.autumn.bean.BeanPropertyMaker;
import com.technoirarts.autumn.eval.EvalPropertyMaker;
import com.technoirarts.autumn.exception.ContextLoadException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public abstract class MapApplicationContext extends BasicApplicationContext {

    protected BeanPropertyMaker maker;

    private Exception registerBeanException;

    public MapApplicationContext() {
        super();

        this.maker = new EvalPropertyMaker(this.registry);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void processNode(Object node) {
        traverseBeanGraph((Map<String, Object>) node);
    }

    private void traverseBeanGraph(Map<String, Object> map) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        while (!entries.isEmpty()) {
            Integer size = entries.size();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                if (tryRegisterBean(iterator.next())) {
                    iterator.remove();
                }
            }
            if (size == entries.size()) {
                throw new ContextLoadException("Cannot resolve all dependencies, unresolved: " + entries.toString(), registerBeanException);
            }
        }
    }

    private boolean tryRegisterBean(Map.Entry<String, Object> entry) {
        try {
            final String beanId = entry.getKey();
            final Object beanDescription = entry.getValue();
            final Object beanValue = maker.make(beanDescription);
            registry.register(beanId, beanValue);
            return true;
        } catch (Exception e) {
            saveException(e);
            return false;
        }
    }

    private void saveException(Exception exception) {
        if (registerBeanException == null) {
            registerBeanException = exception;
        } else {
            registerBeanException.addSuppressed(exception);
        }
    }
}
