package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.BeanConstructionException;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class ClassPropertyEvaluator extends DescriptorPropertyEvaluator {

    public ClassPropertyEvaluator(PropertyMaker maker) {
        super(maker);
    }

    @Override
    protected String getDescriptor() {
        return "$class";
    }

    @Override
    public Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) {
        String className = (String) descriptor;
        List constructorArguments = (List) maker.makeValue(rest.values());
        return constructObject(className, constructorArguments);
    }

    private Object constructObject(String className, Collection args) {
        try {
            Class<?> clazz = Class.forName(className);
            if (args == null || args.isEmpty()) {
                return clazz.getConstructor().newInstance();
            }
            return constructObject(clazz, args);
        } catch (Exception e) {
            throw new BeanConstructionException("Cannot construct class: " + className + " with args: " + args);
        }
    }

    private Object constructObject(Class<?> clazz, Collection args) {
        try {
            Class<?>[] signature = getConstructorSignature(args);
            Constructor<?> constructor = findSuitableConstructor(clazz, signature);
            return constructor.newInstance(args.toArray());
        } catch (Exception e) {
            throw new BeanConstructionException("Cannot instantiate new bean of class: " + clazz.getSimpleName(), e);
        }
    }

    private Class<?>[] getConstructorSignature(Collection args) {
        ArrayList<Class<?>> signature = new ArrayList<>(args.size());
        for (Object arg : args) {
            signature.add(arg.getClass());
        }
        return signature.toArray(new Class<?>[signature.size()]);
    }

    private Constructor findSuitableConstructor(Class clazz, Class<?>[] signature) throws NoSuchMethodException {
        for (Constructor constructor : clazz.getConstructors()) {
            Class<?>[] parameters = constructor.getParameterTypes();
            if (parameters.length == signature.length) {
                if (areSuitableParameters(parameters, signature)) {
                    return constructor;
                }
            }
        }
        throw new NoSuchMethodException("No suitable constructors are found for class: " + clazz + " with signature: " + Arrays.toString(signature));
    }

    private boolean areSuitableParameters(Class<?>[] parameters, Class<?>[] signature) {
        for (int i = 0; i < parameters.length; ++i) {
            if (!parameters[i].isAssignableFrom(signature[i])) {
                return false;
            }
        }
        return true;
    }
}
