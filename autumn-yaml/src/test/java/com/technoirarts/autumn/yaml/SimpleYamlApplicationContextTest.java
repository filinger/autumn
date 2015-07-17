package com.technoirarts.autumn.yaml;

import com.technoirarts.autumn.ApplicationContext;
import com.technoirarts.autumn.bean.ClosedTestBean;
import com.technoirarts.autumn.bean.ImmutableTestBean;
import com.technoirarts.autumn.bean.InitializingBean;
import com.technoirarts.autumn.bean.SimpleTestBean;
import com.technoirarts.autumn.bean.TestBean;
import com.technoirarts.autumn.bean.TestBeanCollection;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class SimpleYamlApplicationContextTest {

    private static final String YAML_CONFIG = "simple-test-yaml-config.yaml";

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

        testSimpleBean(context);
        testClosedBean(context);
        testImmutableBean(context);
        testInitializingBean(context);
        testBeanCollection(context);
        testBeanFactory(context);
    }

    private void testSimpleBean(ApplicationContext context) {
        SimpleTestBean bean = context.getBean(SimpleTestBean.class);
        assertThat(bean.getName(), equalTo("SimpleTestBean"));
        assertThat(bean.getScores(), hasItems(1, 2, 3));
    }

    private void testClosedBean(ApplicationContext context) {
        ClosedTestBean bean = context.getBean(ClosedTestBean.class);
        assertThat(bean.getName(), equalTo("ClosedTestBean"));
    }

    private void testImmutableBean(ApplicationContext context) {
        ImmutableTestBean bean = context.getBean(ImmutableTestBean.class);
        assertThat(bean.getName(), equalTo("ImmutableTestBean"));
        assertThat(bean.getAge(), equalTo(42));
        assertThat(bean.getRelative(), Matchers.<TestBean>is(context.getBean(ClosedTestBean.class)));
    }

    private void testInitializingBean(ApplicationContext context) {
        InitializingBean bean = context.getBean(InitializingBean.class);
        assertThat(bean.getBeanRef(), notNullValue());
        assertThat(bean.getName(), equalTo(bean.getBeanRef().getName()));
    }

    private void testBeanCollection(ApplicationContext context) {
        TestBeanCollection bean = context.getBean(TestBeanCollection.class);
        TestBean simpleBean = (TestBean) context.getBean("simpleBean");
        TestBean closedBean = (TestBean) context.getBean("closedBean");
        TestBean immutableBean = (TestBean) context.getBean("immutableBean");
        assertThat(bean.allTestBeans, hasItems(simpleBean, closedBean, immutableBean));
    }

    @SuppressWarnings("unchecked")
    private void testBeanFactory(ApplicationContext context) {
        List<TestBean> factoredBeans = (List<TestBean>) context.getBean("factoredBeans");
        assertThat(factoredBeans, Matchers.<TestBean>hasItems(hasProperty("name", is("FactoredBean"))));
    }
}
