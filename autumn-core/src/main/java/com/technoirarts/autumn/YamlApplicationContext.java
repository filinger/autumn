package com.technoirarts.autumn;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.parser.Parser;
import com.technoirarts.autumn.bean.Bean;
import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.bean.MapBeanRegistry;
import com.technoirarts.autumn.exception.ContextLoadException;

import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class YamlApplicationContext implements ApplicationContext {

    protected BeanRegistry registry;
    protected YamlReader reader;

    private Map<String, Object> anchors;
    private List<String> tags;

    public YamlApplicationContext() {
        this.registry = new MapBeanRegistry();
    }

    public YamlApplicationContext(Reader yamlReader) {
        this();
        setResourceReader(yamlReader);
        loadAll();
    }

    @Override
    public void setResourceReader(Reader resourceReader) {
        reader = new YamlReader(resourceReader);
        reader.getConfig().setPrivateFields(true);
        anchors = getAnchors(reader);
        tags = getTags(reader);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getAnchors(YamlReader reader) {
        try {
            Field anchors = YamlReader.class.getDeclaredField("anchors");
            anchors.setAccessible(true);
            return (Map<String, Object>) anchors.get(reader);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Was unable to extract anchors field from YamlReader, illegal access", e);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Was unable to extract anchors field from YamlReader, no such field", e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getTags(YamlReader reader) {
        try {
            Field parser = YamlReader.class.getDeclaredField("parser");
            parser.setAccessible(true);
            Field tags = Parser.class.getDeclaredField("tags");
            tags.setAccessible(true);
            return (List<String>) tags.get(parser.get(reader));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Was unable to extract tags field from YamlReader, illegal access", e);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Was unable to extract tags field from YamlReader, no such field", e);
        }
    }

    @Override
    public void loadAll() {
        try {
            Object document = reader.read();
            while (document != null) {
                processDocument(document);
                document = reader.read();
            }
        } catch (Exception e) {
            throw new ContextLoadException("Context load failed", e);
        }
    }

    @Override
    public void loadNext() {
        try {
            Object document = reader.read();
            if (document != null) {
                processDocument(document);
            }
        } catch (Exception e) {
            throw new ContextLoadException("Context load failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    protected void processDocument(Object document) {
        for (Map.Entry<String, Object> anchor : anchors.entrySet()) {
            if (anchor.getValue().equals(document)) {
                registry.register(new Bean(anchor.getKey(), document.getClass(), document));
                return;
            }

        }
        registry.register(new Bean(makeBeanId(), document.getClass(), document));
    }

    private String makeBeanId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Object getBean(String id) {
        Bean bean = registry.findById(id);
        return bean != null ? bean.getValue() : null;
    }

    @Override
    public <T> T getBean(Class<T> type) {
        Bean<T> bean = registry.findByType(type);
        return bean != null ? bean.getValue() : null;
    }

    @Override
    public <T> List<T> getBeans(Class<T> type) {
        List<Bean<T>> beans = registry.findOfType(type);
        ArrayList<T> values = new ArrayList<>(beans.size());
        for (Bean<T> bean : beans) {
            values.add(bean.getValue());
        }
        return values;
    }

    public Map<String, Object> getAnchors() {
        return anchors;
    }

    public List<String> getTags() {
        return tags;
    }
}
