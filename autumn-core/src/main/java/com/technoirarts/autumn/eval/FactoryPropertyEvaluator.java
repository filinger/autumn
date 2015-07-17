package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class FactoryPropertyEvaluator extends DescriptorPropertyEvaluator {

    private final BeanRegistry registry;
    private final Pattern splitPattern;

    public FactoryPropertyEvaluator(EvalPropertyMaker maker, BeanRegistry registry) {
        super(maker);
        this.registry = registry;
        this.splitPattern = Pattern.compile(Pattern.quote("."));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        if (descriptor instanceof String) {
            String[] factory = splitPattern.split((String) descriptor);
            if (factory.length == 2) {
                return (T) callFactoryMethod(getFactoryBean(factory[0]), factory[1]);
            }
            throw new PropertyEvaluationException(this, "received descriptor with wrong format (should be 'factoryBean.factoryMethod'): " + descriptor);
        }
        throw new PropertyEvaluationException(this, "received descriptor of unknown type: " + descriptor);
    }

    private Object getFactoryBean(String id) throws PropertyEvaluationException {
        Object resolved = registry.findById(id);
        if (resolved != null) {
            return resolved;
        }
        throw new PropertyEvaluationException(this, "was unable to find factory by id: " + id);
    }

    private Object callFactoryMethod(Object factoryBean, String factoryMethodName) throws PropertyEvaluationException {
        try {
            return factoryBean.getClass().getMethod(factoryMethodName).invoke(factoryBean);
        } catch (ReflectiveOperationException e) {
            throw new PropertyEvaluationException(this, "was unable to call factory method: " + factoryMethodName + " on factory: " + factoryBean, e);
        }
    }

    @Override
    protected String getDescriptor() {
        return "$factory";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Beans.Any.class);
    }
}
