package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.bean.PackageRegistry;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class InjectPropertyEvaluator extends DescriptorPropertyEvaluator {

    private final BeanRegistry registry;
    private final PackageRegistry packages;

    public InjectPropertyEvaluator(EvalPropertyMaker maker, BeanRegistry registry, PackageRegistry packages) {
        super(maker);
        this.registry = registry;
        this.packages = packages;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        String idOrType = (String) descriptor;
        if (idOrType.isEmpty()) {
            return autoInject(typeAdvice);
        } else if (Collection.class.isAssignableFrom(typeAdvice)) {
            return findOfType(idOrType, typeAdvice);
        } else {
            return (T) findByIdOrType(idOrType);
        }
    }

    private <T> T autoInject(Class<T> typeAdvice) throws PropertyEvaluationException {
        throw new PropertyEvaluationException(this, "automatic inject is not currently implemented");
    }

    private Object findByIdOrType(String idOrType) throws PropertyEvaluationException {
        Object resolved = registry.findById(idOrType);
        if (resolved != null) {
            return resolved;
        }
        return findByType(idOrType);
    }

    private Object findByType(String type) throws PropertyEvaluationException {
        try {
            Class<?> clazz = packages.findClass(type);
            Object resolved = registry.findByType(clazz);
            if (resolved != null) {
                return resolved;
            }
        } catch (ClassNotFoundException e) {
            throw new PropertyEvaluationException(this, "was unable to find bean type: " + type, e);
        }
        throw new PropertyEvaluationException(this, "was unable to find bean by type: " + type);
    }

    private <T> T findOfType(String beanType, Class<T> collectionType) throws PropertyEvaluationException {
        try {
            Class<?> clazz = packages.findClass(beanType);
            List<?> beans = registry.findOfType(clazz);
            return Beans.getCollectionInstance(beans, collectionType);
        } catch (InstantiationException e) {
            throw new PropertyEvaluationException(this, "can't instantiate collection of type: " + collectionType, e);
        } catch (ClassNotFoundException e) {
            throw new PropertyEvaluationException(this, "can't find bean type: " + beanType, e);
        }
    }

    @Override
    protected String getDescriptor() {
        return "$inject";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Beans.Any.class);
    }
}
