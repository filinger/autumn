package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/16/2015
 * @since 1.0
 */
public class InitPropertyEvaluator extends DescriptorPropertyEvaluator {

    public InitPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        String init = (String) descriptor;
        Object object = maker.make(rest);
        try {
            Method initMethod = object.getClass().getDeclaredMethod(init);
            initMethod.invoke(object);
        } catch (ReflectiveOperationException e) {
            throw new PropertyEvaluationException(this, "can't initialize object: " + object + " with method: " + init, e);
        }
        return (T) object;
    }

    @Override
    protected String getDescriptor() {
        return "$init";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Object.class);
    }
}
