package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class ConstructPropertyEvaluator extends DescriptorPropertyEvaluator {

    public ConstructPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    protected String getDescriptor() {
        return "$construct";
    }

    @Override
    protected Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) throws PropertyEvaluationException {
        Object beanInstance = maker.make(descriptor);
        setBeanProperties(beanInstance, rest);
        return beanInstance;
    }

    private void setBeanProperties(Object instance, Map<String, Object> properties) throws PropertyEvaluationException {
        try {
            Class clazz = instance.getClass();
            for (Map.Entry<String, Object> property : properties.entrySet()) {
                Field field = clazz.getDeclaredField(property.getKey());
                field.setAccessible(true);
                Object value = maker.make(property.getValue());
                field.set(instance, value);
            }
        } catch (IllegalAccessException e) {
            throw new PropertyEvaluationException(this, "cannot access field for class: " + instance.getClass());
        } catch (NoSuchFieldException e) {
            throw new PropertyEvaluationException(this, "cannot find field: " + instance.getClass());
        }
    }
}
