package com.technoirarts.autumn.bean;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/10/2015
 * @since 1.0
 */
public interface BeanPropertyMaker {

    Object make(Object property) throws PropertyEvaluationException;

    <T> T make(Object property, Class<T> type) throws PropertyEvaluationException;
}
