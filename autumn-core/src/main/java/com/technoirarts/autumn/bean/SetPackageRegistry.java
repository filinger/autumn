package com.technoirarts.autumn.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class SetPackageRegistry implements PackageRegistry {

    private final Set<Package> packages;

    public SetPackageRegistry() {
        packages = new HashSet<>();
    }

    @Override
    public void register(String packageName) {
        Package pack = Package.getPackage(packageName);
        if (pack == null) {
            throw new IllegalArgumentException("unknown package: " + packageName);
        }
        packages.add(pack);
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        return Beans.forName(className, packages);
    }
}
