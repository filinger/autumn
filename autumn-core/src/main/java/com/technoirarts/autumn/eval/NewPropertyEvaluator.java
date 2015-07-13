package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.bean.PackageRegistry;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class NewPropertyEvaluator extends DescriptorPropertyEvaluator {

    private final PackageRegistry packages;

    public NewPropertyEvaluator(EvalPropertyMaker maker, PackageRegistry packages) {
        super(maker);
        this.packages = packages;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        String className = (String) descriptor;
        Collection sortedArguments = new TreeMap<>(rest).values();
        if (!sortedArguments.isEmpty()) {
            sortedArguments = maker.make(sortedArguments, Collection.class);
        }
        try {
            Class<?> clazz = packages.findClass(className);
            return (T) Beans.getInstance(clazz, sortedArguments);
        } catch (ReflectiveOperationException e) {
            throw new PropertyEvaluationException(this, "cannot instantiate class: " + className, e);
        }
    }

    @Override
    protected String getDescriptor() {
        return "$new";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Object.class);
    }
}
