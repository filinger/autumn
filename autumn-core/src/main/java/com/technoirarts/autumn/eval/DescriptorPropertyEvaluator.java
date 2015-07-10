package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.HashMap;
import java.util.Map;

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

    protected abstract String getDescriptor();

    @Override
    @SuppressWarnings("unchecked")
    public boolean canEvaluate(Object property) {
        return property instanceof Map && ((Map<String, Object>) property).containsKey(getDescriptor());
    }

    @Override
    protected Object checkedEvaluate(Object property) throws PropertyEvaluationException {
        @SuppressWarnings("unchecked")
        Map<String, Object> properties = (Map<String, Object>) property;
        Map<String, Object> propertiesCopy = new HashMap<>(properties);
        Object descriptor = propertiesCopy.remove(getDescriptor());
        return evaluateDescriptor(descriptor, propertiesCopy);
    }

    protected abstract Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) throws PropertyEvaluationException;

    @Override
    protected <T> T checkedEvaluate(Object property, Class<T> typeAdvice) throws PropertyEvaluationException {
        @SuppressWarnings("unchecked")
        Map<String, Object> properties = (Map<String, Object>) property;
        Map<String, Object> propertiesCopy = new HashMap<>(properties);
        Object descriptor = propertiesCopy.remove(getDescriptor());
        return evaluateDescriptor(descriptor, propertiesCopy, typeAdvice);
    }

    protected abstract <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice) throws PropertyEvaluationException;
}
