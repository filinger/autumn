package com.technoirarts.autumn.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class Beans {

    private Beans() {
    }

    public static boolean areAssignableFrom(Class<?>[] classes, Class<?>[] others) {
        for (int i = 0; i < classes.length; ++i) {
            if (!classes[i].isAssignableFrom(others[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAssignableFrom(Class<?> clazz, Collection<Class<?>> others) {
        if (others.iterator().hasNext() && others.iterator().next().equals(Any.class)) {
            return true;
        }

        for (Class<?> other : others) {
            if (clazz.isAssignableFrom(other)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAssignableTo(Class<?> clazz, Collection<Class<?>> others) {
        if (clazz.equals(Any.class)) {
            return true;
        }

        for (Class<?> other : others) {
            if (other.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    public static Class<?> forName(String className, Collection<Package> packages) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            for (Package pack : packages) {
                String canonicalClassName = pack.getName() + "." + className;
                try {
                    return Class.forName(canonicalClassName);
                } catch (ClassNotFoundException ignored) {
                }
            }
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCollectionInstance(Collection source, Class<T> desiredType) throws InstantiationException {
        Class<?> sourceType = source.getClass();
        if (desiredType.isAssignableFrom(sourceType)) {
            return (T) source;
        } else if (desiredType.isAssignableFrom(ArrayList.class)) {
            return (T) new ArrayList<>(source);
        } else if (desiredType.isAssignableFrom(LinkedList.class)) {
            return (T) new LinkedList<>(source);
        } else if (desiredType.isAssignableFrom(HashSet.class)) {
            return (T) new HashSet(source);
        } else if (desiredType.isAssignableFrom(TreeSet.class)) {
            return (T) new TreeSet<>(source);
        }
        throw new InstantiationException("cannot convert " + source.getClass() + " to " + desiredType);
    }

    public static Object getInstance(Class<?> clazz, Collection args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (args == null || args.isEmpty()) {
            return clazz.getConstructor().newInstance();
        }
        Class<?>[] signature = getConstructorSignature(args);
        Constructor<?> constructor = getConstructor(clazz, signature);
        return constructor.newInstance(args.toArray());
    }

    public static Class<?>[] getConstructorSignature(Collection args) {
        ArrayList<Class<?>> signature = new ArrayList<>(args.size());
        for (Object arg : args) {
            signature.add(arg.getClass());
        }
        return signature.toArray(new Class<?>[signature.size()]);
    }

    public static Constructor getConstructor(Class clazz, Class<?>[] signature) throws NoSuchMethodException {
        for (Constructor constructor : clazz.getConstructors()) {
            Class<?>[] parameters = constructor.getParameterTypes();
            if (parameters.length == signature.length) {
                if (areAssignableFrom(parameters, signature)) {
                    return constructor;
                }
            }
        }
        throw new NoSuchMethodException("No suitable constructors are found for class: " + clazz + " with signature: " + Arrays.toString(signature));
    }

    /**
     * Indicates that bean type is assignableFrom any other type.
     */
    public static class Any {
    }

    /**
     * Bean type for the 'null' value.
     */
    public static class Null {
    }
}
