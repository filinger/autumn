package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public abstract class BasicPropertyEvaluator implements PropertyEvaluator {

    protected final EvalPropertyMaker maker;
    protected final Set<Class<?>> returnTypes;
    protected final Set<Class<?>> acceptTypes;

    public BasicPropertyEvaluator(EvalPropertyMaker maker) {
        this.maker = maker;
        this.returnTypes = getReturnTypes();
        this.acceptTypes = getAcceptTypes();
    }

    @Override
    public boolean canReturn(Class<?> valueType) {
        return Beans.isAssignableFrom(valueType, returnTypes);
    }

    @Override
    public boolean canAccept(Class<?> propertyType) {
        return Beans.isAssignableTo(propertyType, acceptTypes);
    }

    @Override
    public boolean canEvaluate(Object property, Class<?> typeAdvice) {
        return canAccept(property.getClass()) && canReturn(typeAdvice);
    }

    @Override
    public <T> T evaluate(Object property, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        if (canEvaluate(property, typeAdvice)) {
            return checkedEvaluate(property, typeAdvice, typeParameters);
        }
        throw new PropertyEvaluationException(this, "this evaluator is not intended for property: " + property + " and type: " + typeAdvice);
    }

    protected abstract <T> T checkedEvaluate(Object property, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException;

    protected abstract Set<Class<?>> getReturnTypes();

    protected abstract Set<Class<?>> getAcceptTypes();
}
