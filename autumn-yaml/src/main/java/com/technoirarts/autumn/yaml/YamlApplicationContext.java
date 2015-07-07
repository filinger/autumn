package com.technoirarts.autumn.yaml;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.parser.Parser;
import com.technoirarts.autumn.BasicApplicationContext;
import com.technoirarts.autumn.bean.Bean;

import java.io.Reader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class YamlApplicationContext extends BasicApplicationContext {

    private final Map<String, Object> anchors;
    private final List<String> tags;

    public YamlApplicationContext(Reader resourceReader) {
        super();

        YamlReader yamlReader = new YamlReader(resourceReader);
        reader = new YamlNodeReader(yamlReader);
        anchors = getAnchors(yamlReader);
        tags = getTags(yamlReader);
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
    @SuppressWarnings("unchecked")
    protected void processNode(Object node) {
        for (Map.Entry<String, Object> anchor : anchors.entrySet()) {
            if (anchor.getValue().equals(node)) {
                registry.register(new Bean(anchor.getKey(), node.getClass(), node));
                return;
            }

        }
        registry.register(new Bean(makeBeanId(), node.getClass(), node));
    }

    private String makeBeanId() {
        return UUID.randomUUID().toString();
    }

    public Map<String, Object> getAnchors() {
        return anchors;
    }

    public List<String> getTags() {
        return tags;
    }
}
