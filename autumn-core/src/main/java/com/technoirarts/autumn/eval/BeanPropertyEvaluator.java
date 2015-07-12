package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class BeanPropertyEvaluator extends DescriptorPropertyEvaluator {

    public BeanPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice) throws PropertyEvaluationException {
        T beanInstance = maker.make(descriptor, typeAdvice);
        setBeanProperties(beanInstance, rest);
        return beanInstance;
    }

    private void setBeanProperties(Object instance, Map<String, Object> properties) throws PropertyEvaluationException {
        try {
            Class clazz = instance.getClass();
            for (Map.Entry<String, Object> property : properties.entrySet()) {
                Field field = clazz.getDeclaredField(property.getKey());
                field.setAccessible(true);
                Object value = maker.make(property.getValue(), field.getType());
                field.set(instance, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new PropertyEvaluationException(this, "cannot set bean property: " + instance.getClass(), e);
        }
    }

    @Override
    protected String getDescriptor() {
        return "$bean";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(Object.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Object.class);
    }
}
