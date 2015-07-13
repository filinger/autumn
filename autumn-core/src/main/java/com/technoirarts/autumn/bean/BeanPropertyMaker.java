package com.technoirarts.autumn.bean;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/10/2015
 * @since 1.0
 */
public interface BeanPropertyMaker {

    /**
     * Make the property value.
     *
     * @param property property to process
     * @return value of the specified property
     * @throws PropertyEvaluationException if property can't be properly evaluated
     */
    Object make(Object property) throws PropertyEvaluationException;

    /**
     * Make the property value.
     *
     * @param property       property to process
     * @param type           desired value type
     * @param typeParameters generic type parameters if any
     * @param <T>            desired value type
     * @return value of the specified property
     * @throws PropertyEvaluationException if property can't be properly evaluated
     */
    <T> T make(Object property, Class<T> type, Class<?>... typeParameters) throws PropertyEvaluationException;
}
