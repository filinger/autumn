package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.BeanConstructionException;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class ConstructPropertyEvaluator extends DescriptorPropertyEvaluator {

    public ConstructPropertyEvaluator(PropertyMaker maker) {
        super(maker);
    }

    @Override
    protected String getDescriptor() {
        return "$construct";
    }

    @Override
    protected Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) {
        Object beanInstance = maker.makeValue(descriptor);
        setBeanProperties(beanInstance, rest);
        return beanInstance;
    }

    private void setBeanProperties(Object instance, Map<String, Object> properties) {
        try {
            Class clazz = instance.getClass();
            for (Map.Entry<String, Object> property : properties.entrySet()) {
                Field field = clazz.getDeclaredField(property.getKey());
                field.setAccessible(true);
                Object value = maker.makeValue(property.getValue());
                field.set(instance, value);
            }
        } catch (Exception e) {
            throw new BeanConstructionException("Cannot set bean properties", e);
        }
    }
}
