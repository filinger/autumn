package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    public Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) throws PropertyEvaluationException {
        String className = (String) descriptor;
        List constructorArguments = (List) maker.makeValue(rest.values());
        return constructObject(className, constructorArguments);
    }

    private Object constructObject(String className, Collection args) throws PropertyEvaluationException {
        try {
            Class<?> clazz = Class.forName(className);
            return constructObject(clazz, args);
        } catch (ClassNotFoundException e) {
            throw new PropertyEvaluationException(this, "cannot find class: " + className, e);
        } catch (InvocationTargetException e) {
            throw new PropertyEvaluationException(this, "cannot invoke constructor of class: " + className, e);
        } catch (NoSuchMethodException e) {
            throw new PropertyEvaluationException(this, "cannot find constructor of class: " + className, e);
        } catch (InstantiationException e) {
            throw new PropertyEvaluationException(this, "cannot make instance of class: " + className, e);
        } catch (IllegalAccessException e) {
            throw new PropertyEvaluationException(this, "cannot access constructor of class: " + className, e);
        }
    }

    private Object constructObject(Class<?> clazz, Collection args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (args == null || args.isEmpty()) {
            return clazz.getConstructor().newInstance();
        }
        Class<?>[] signature = getConstructorSignature(args);
        Constructor<?> constructor = findSuitableConstructor(clazz, signature);
        return constructor.newInstance(args.toArray());
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
