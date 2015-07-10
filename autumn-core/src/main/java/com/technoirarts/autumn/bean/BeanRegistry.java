package com.technoirarts.autumn.bean;

import java.util.List;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public interface BeanRegistry {

    void register(Bean<?> bean);

    Bean<?> findById(String beanId);

    Bean<?> findByType(String beanType);

    List<Bean<?>> findOfType(String beanType);

    <T> Bean<T> findByType(Class<T> beanType);

    <T> List<Bean<T>> findOfType(Class<T> beanType);
}
