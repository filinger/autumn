package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public interface PropertyEvaluator {

    /**
     * Checks if this evaluator can possibly produce return value of the specified type.
     *
     * @param valueType desired return value type
     * @return true if evaluator may return value of specified type, false otherwise
     */
    boolean canReturn(Class<?> valueType);

    /**
     * Checks if this evaluator can possibly consume property of the specified type.
     *
     * @param propertyType desired property type
     * @return true if property of specified type can be accepted, false otherwise
     */
    boolean canAccept(Class<?> propertyType);

    /**
     * Checks if this evaluator can evaluate specified property to the value of specified type.
     *
     * @param property   property to evaluate
     * @param typeAdvice class of the desired return value type, may be null
     * @return true if property can be evaluated or false otherwise
     */
    boolean canEvaluate(Object property, Class<?> typeAdvice);

    /**
     * Tries to evaluate specified property to the value of specified type.
     *
     * @param <T>        desired return value type, unknown if typeAdvice is null
     * @param property   property to evaluate
     * @param typeAdvice class of the desired return value type, may be null
     * @param typeParameters generic type parameters if any
     * @return evaluated property
     * @throws PropertyEvaluationException if property can't be evaluated
     */
    <T> T evaluate(Object property, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException;
}
