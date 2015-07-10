package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public interface PropertyEvaluator {

    boolean canEvaluate(Object property);

    Object evaluate(Object property) throws PropertyEvaluationException;

    boolean canEvaluate(Object property, Class<?> typeAdvice);

    <T> T evaluate(Object property, Class<T> typeAdvice) throws PropertyEvaluationException;
}
