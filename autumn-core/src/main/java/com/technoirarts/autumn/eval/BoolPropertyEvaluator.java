package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

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
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        return (T) Boolean.valueOf((String) descriptor);
    }

    @Override
    protected String getDescriptor() {
        return "$bool";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Boolean.class);
    }
}
