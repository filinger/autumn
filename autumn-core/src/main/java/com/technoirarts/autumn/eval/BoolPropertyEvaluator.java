package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Map;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class BoolPropertyEvaluator extends DescriptorPropertyEvaluator {

    public BoolPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    protected String getDescriptor() {
        return "$bool";
    }

    @Override
    protected Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) throws PropertyEvaluationException {
        return Boolean.parseBoolean((String) descriptor);
    }

    @Override
    public boolean canEvaluate(Object property, Class<?> typeAdvice) {
        return canEvaluate(property) && (typeAdvice.isAssignableFrom(Boolean.class) || typeAdvice.isAssignableFrom(boolean.class));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice) throws PropertyEvaluationException {
        return (T) evaluateDescriptor(descriptor, rest);
    }
}
