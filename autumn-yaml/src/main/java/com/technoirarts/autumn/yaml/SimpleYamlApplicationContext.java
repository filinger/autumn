package com.technoirarts.autumn.yaml;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.technoirarts.autumn.BasicApplicationContext;
import com.technoirarts.autumn.bean.Bean;
import com.technoirarts.autumn.exception.ContextLoadException;

import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class SimpleYamlApplicationContext extends BasicApplicationContext {

    public SimpleYamlApplicationContext(Reader resourceReader) {
        reader = new YamlNodeReader(new YamlReader(resourceReader));
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
                throw new ContextLoadException("Cannot resolve all dependencies, unresolved: " + entries.toString());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private boolean tryRegisterBean(Map.Entry<String, Object> entry) {
        try {
            final String beanName = entry.getKey();
            final Object beanDescription = entry.getValue();
            final Object beanValue = maker.makeValue(beanDescription);
            registry.register(new Bean(beanName, beanValue.getClass(), beanValue));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
