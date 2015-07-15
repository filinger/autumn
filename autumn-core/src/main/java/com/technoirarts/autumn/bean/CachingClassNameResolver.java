package com.technoirarts.autumn.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class CachingClassNameResolver implements ClassNameResolver {

    private final Set<Package> packages;
    private final Map<String, Class<?>> cache;
    private final ClassLoader classLoader;

    public CachingClassNameResolver() {
        packages = new HashSet<>();
        cache = new HashMap<>();
        classLoader = this.getClass().getClassLoader();
    }

    @Override
    public void addPackage(String packageName) {
        Package pack = Package.getPackage(packageName);
        if (pack == null) {
            throw new IllegalArgumentException("unknown package: " + packageName);
        }
        packages.add(pack);
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        Class<?> clazz = cache.get(className);
        if (clazz != null) {
            return clazz;
        }

        clazz = Beans.forName(className, packages, classLoader);
        cache.put(className, clazz);
        return clazz;
    }
}
