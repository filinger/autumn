package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class ArrayPropertyEvaluator extends BasicPropertyEvaluator {

    public ArrayPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    public boolean canEvaluate(Object property) {
        return property instanceof Collection;
    }

    @Override
    protected Object checkedEvaluate(Object property) throws PropertyEvaluationException {
        Collection properties = (Collection) property;
        ArrayList<Object> evaluated = new ArrayList<>(properties.size());
        for (Object prop : properties) {
            evaluated.add(maker.make(prop));
        }
        return evaluated;
    }

    @Override
    public boolean canEvaluate(Object property, Class<?> typeAdvice) {
        return canEvaluate(property) && (List.class.isAssignableFrom(typeAdvice) || Set.class.isAssignableFrom(typeAdvice));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T checkedEvaluate(Object property, Class<T> typeAdvice) throws PropertyEvaluationException {
        List evaluatedProperties = (ArrayList) checkedEvaluate(property);
        if (List.class.isAssignableFrom(typeAdvice)) {
            return (T) evaluatedProperties;
        } else if (Set.class.isAssignableFrom(typeAdvice)) {
            return (T) new HashSet(evaluatedProperties);
        }
        throw new PropertyEvaluationException(this, "got unknown collection type to make: " + typeAdvice.getCanonicalName() + " for property: " + property);
    }
}
