package com.technoirarts.autumn.bean;

import java.util.List;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public interface BeanRegistry {

    void register(String beanId, Object bean);

    Object findById(String beanId);

    Object findByType(String beanType);

    List<?> findOfType(String beanType);

    <T> T findByType(Class<T> beanType);

    <T> List<T> findOfType(Class<T> beanType);
}
