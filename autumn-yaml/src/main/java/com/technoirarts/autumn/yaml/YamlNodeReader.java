package com.technoirarts.autumn.yaml;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.technoirarts.autumn.NodeReader;
import com.technoirarts.autumn.exception.ContextLoadException;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/7/2015
 * @since 1.0
 */
public class YamlNodeReader implements NodeReader {

    private final YamlReader reader;

    public YamlNodeReader(YamlReader reader) {
        this.reader = reader;
        reader.getConfig().setPrivateFields(true);
    }

    @Override
    public Object next() {
        try {
            return reader.read();
        } catch (YamlException e) {
            throw new ContextLoadException("Error during parsing YAML configuration", e);
        }
    }
}
