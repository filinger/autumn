package com.technoirarts.autumn.bean;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public interface PackageRegistry {

    void register(String packageName);

    Class<?> findClass(String className) throws ClassNotFoundException;
}
