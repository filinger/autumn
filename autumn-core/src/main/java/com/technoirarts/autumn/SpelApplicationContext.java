package com.technoirarts.autumn;

import com.technoirarts.autumn.bean.Bean;
import com.technoirarts.autumn.bean.BeanRegistryResolver;
import com.technoirarts.autumn.exception.BeanConstructionException;
import com.technoirarts.autumn.exception.BeanNotFoundException;
import com.technoirarts.autumn.exception.ContextLoadException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class SpelApplicationContext extends YamlApplicationContext {

    private ExpressionParser parser;
    private StandardEvaluationContext context;
    private BeanRegistryResolver resolver;

    public SpelApplicationContext() {
        super();
        this.parser = new SpelExpressionParser();
        this.context = new StandardEvaluationContext();
        this.resolver = new BeanRegistryResolver(registry);

        context.setBeanResolver(resolver);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void processDocument(Object document) {
        traverseBeanGraph((Map<String, Object>) document);
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
            final Object beanValue = evaluateProperty(beanDescription);
            registry.register(new Bean(beanName, beanValue.getClass(), beanValue));
            return true;
        } catch (BeanNotFoundException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private Object evaluateProperty(Object description) {
        if (description instanceof String) {
            return parser.parseExpression((String) description).getValue(context);
        } else if (description instanceof Map) {
            return evaluateNewBean((Map<String, Object>) description);
        }
        throw new ContextLoadException("Received bean property that is neither string or map");
    }

    @SuppressWarnings("unchecked")
    private Object evaluateNewBean(Map<String, Object> properties) {
        if (!properties.containsKey("$construct")) {
            throw new BeanConstructionException("Cannot construct a bean without $construct declaration");
        }

        Object beanInstance = evaluateProperty(properties.remove("$construct"));
        evaluateBeanProperties(beanInstance, properties);
        return beanInstance;
    }

    private void evaluateBeanProperties(Object bean, Map<String, Object> properties) {
        context.setRootObject(bean);
        for (Map.Entry<String, Object> property : properties.entrySet()) {
            String name = property.getKey();
            Object description = property.getValue();
            parser.parseExpression(name).setValue(context, evaluateProperty(description));
        }
    }
}
