package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class PrimitivePropertyEvaluator extends BasicPropertyEvaluator {

    public PrimitivePropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T checkedEvaluate(Object property, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        String string = (String) property;
        if (typeAdvice.isAssignableFrom(String.class)) {
            return (T) string;
        } else if (typeAdvice.isAssignableFrom(Boolean.class)) {
            return (T) Boolean.valueOf(string);
        } else if (typeAdvice.isAssignableFrom(Integer.class)) {
            return (T) Integer.valueOf(string);
        } else if (typeAdvice.isAssignableFrom(Float.class)) {
            return (T) Float.valueOf(string);
        }
        throw new PropertyEvaluationException(this, "can't parse value of: " + string + " to type: " + typeAdvice);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        HashSet<Class<?>> returnTypes = new HashSet<>(4);
        returnTypes.add(String.class);
        returnTypes.add(Boolean.class);
        returnTypes.add(Integer.class);
        returnTypes.add(Float.class);
        return returnTypes;
    }

    @Override
    protected Set<Class<?>> getAcceptTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }
}
