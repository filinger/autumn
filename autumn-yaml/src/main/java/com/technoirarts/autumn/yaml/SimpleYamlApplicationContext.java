package com.technoirarts.autumn.yaml;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.technoirarts.autumn.MapApplicationContext;

import java.io.Reader;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class SimpleYamlApplicationContext extends MapApplicationContext {

    public SimpleYamlApplicationContext(Reader resourceReader) {
        reader = new YamlNodeReader(new YamlReader(resourceReader));
    }
}
