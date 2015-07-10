package com.technoirarts.autumn.bean;

import java.util.List;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/7/2015
 * @since 1.0
 */
public interface BeanValueResolver {

    Object getValueById(String beanId);

    Object getValueByType(String beanType);

    List<?> getValuesByType(String beanType);

    <T> T getValueByType(Class<T> beanType);

    <T> List<T> getValuesByType(Class<T> beanType);
}
