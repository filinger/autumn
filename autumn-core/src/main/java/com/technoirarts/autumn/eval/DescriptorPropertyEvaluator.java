package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public abstract class DescriptorPropertyEvaluator extends BasicPropertyEvaluator {

    public DescriptorPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean canEvaluate(Object property, Class<?> typeAdvice) {
        boolean preCheck = super.canEvaluate(property, typeAdvice);
        if (preCheck) {
            Object descriptorValue = ((Map<String, Object>) property).get(getDescriptor());
            if (descriptorValue != null) {
                return Beans.isAssignableTo(descriptorValue.getClass(), getDescriptorTypes());
            }
        }
        return false;
    }

    @Override
    protected <T> T checkedEvaluate(Object property, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        @SuppressWarnings("unchecked")
        Map<String, Object> properties = (Map<String, Object>) property;
        Map<String, Object> propertiesCopy = new HashMap<>(properties);
        Object descriptor = propertiesCopy.remove(getDescriptor());
        return evaluateDescriptor(descriptor, propertiesCopy, typeAdvice, typeParameters);
    }

    @Override
    protected Set<Class<?>> getAcceptTypes() {
        return Collections.<Class<?>>singleton(Map.class);
    }

    protected abstract <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException;

    protected abstract String getDescriptor();

    protected abstract Set<Class<?>> getDescriptorTypes();
}
