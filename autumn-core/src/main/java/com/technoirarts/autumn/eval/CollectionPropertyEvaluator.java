package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class CollectionPropertyEvaluator extends BasicPropertyEvaluator {

    public CollectionPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T checkedEvaluate(Object property, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        Class<?> elementType = Object.class;
        if (typeParameters.length == 1) {
            elementType = typeParameters[0];
        }
        ArrayList evaluatedProperties = evaluateProperties((Collection) property, elementType);
        try {
            return Beans.getCollectionInstance(evaluatedProperties, typeAdvice);
        } catch (InstantiationException e) {
            throw new PropertyEvaluationException(this, "can't make collection of type: " + typeAdvice, e);
        }
    }

    protected <T> ArrayList<T> evaluateProperties(Collection properties, Class<T> elementType) throws PropertyEvaluationException {
        ArrayList<T> evaluated = new ArrayList<>(properties.size());
        for (Object prop : properties) {
            evaluated.add(maker.make(prop, elementType));
        }
        return evaluated;
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        HashSet<Class<?>> returnTypes = new HashSet<>(2);
        returnTypes.add(ArrayList.class);
        returnTypes.add(HashSet.class);
        return returnTypes;
    }

    @Override
    protected Set<Class<?>> getAcceptTypes() {
        return Collections.<Class<?>>singleton(Collection.class);
    }
}
