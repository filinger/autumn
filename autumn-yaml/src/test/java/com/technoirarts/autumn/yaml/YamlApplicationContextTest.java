package com.technoirarts.autumn.yaml;

import com.technoirarts.autumn.ApplicationContext;
import com.technoirarts.autumn.bean.*;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class YamlApplicationContextTest {

    private static final String YAML_CONFIG = "test-yaml-config.yaml";

    private Reader yamlReader;

    @Before
    public void prepareYamlReader() {
        InputStream yamlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(YAML_CONFIG);
        yamlReader = new InputStreamReader(yamlStream);
    }

    @Test
    public void testYamlContextLoading() {
        YamlApplicationContext context = new YamlApplicationContext(yamlReader);

        testTagShortcut(context);
        testSimpleBean(context);
        testClosedBean(context);
        testImmutableBean(context);
        testBeanCollection(context);
    }

    private void testTagShortcut(YamlApplicationContext context) {
        context.loadNext();
        assertThat(context.getTags(), hasSize(5));
    }

    private void testSimpleBean(ApplicationContext context) {
        context.loadNext();
        SimpleTestBean bean = context.getBean(SimpleTestBean.class);
        assertThat(bean.getName(), equalTo("SimpleTestBean"));
        assertThat(bean.getScores(), hasItems(1, 2, 3));
    }

    private void testClosedBean(ApplicationContext context) {
        context.loadNext();
        ClosedTestBean bean = context.getBean(ClosedTestBean.class);
        assertThat(bean.getName(), equalTo("ClosedTestBean"));
    }

    private void testImmutableBean(ApplicationContext context) {
        context.loadNext();
        ImmutableTestBean bean = context.getBean(ImmutableTestBean.class);
        assertThat(bean.getName(), equalTo("ImmutableTestBean"));
        assertThat(bean.getAge(), equalTo(42));
        assertThat(bean.getRelative(), Matchers.<TestBean>is(context.getBean(ClosedTestBean.class)));
    }

    private void testBeanCollection(ApplicationContext context) {
        context.loadNext();
        TestBeanCollection bean = context.getBean(TestBeanCollection.class);
        TestBean simpleBean = (TestBean) context.getBean("simpleBean");
        TestBean closedBean = (TestBean) context.getBean("closedBean");
        TestBean immutableBean = (TestBean) context.getBean("immutableBean");
        assertThat(bean.daughters, hasItems(simpleBean, closedBean, immutableBean));
    }
}
