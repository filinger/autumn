package com.technoirarts.autumn.yaml;

import com.technoirarts.autumn.ApplicationContext;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class SimpleYamlInheritedTest {

    private static final String YAML_CONFIG = "simple-yaml-inheritance-config.yaml";

    private Reader yamlReader;

    @Before
    public void prepareYamlReader() {
        InputStream yamlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(YAML_CONFIG);
        yamlReader = new InputStreamReader(yamlStream);
    }

    @Test
    public void testYamlContextLoading() {
        ApplicationContext context = new SimpleYamlApplicationContext(yamlReader);
        context.loadAll();
    }
}
