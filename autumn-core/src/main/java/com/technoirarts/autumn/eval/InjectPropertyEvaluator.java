package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
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

    public InjectPropertyEvaluator(EvalPropertyMaker maker, BeanRegistry registry) {
        super(maker);
        this.registry = registry;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        if (descriptor instanceof String) {
            return (T) getById((String) descriptor);
        } else if (descriptor instanceof Map) {
            Class<?> clazz = maker.make(descriptor, Class.class);
            if (Collection.class.isAssignableFrom(typeAdvice)) {
                return getByType(clazz, typeAdvice);
            }
            return (T) getByType(clazz);
        }
        throw new PropertyEvaluationException(this, "received descriptor of unknown type: " + descriptor);
    }

    private Object getById(String id) throws PropertyEvaluationException {
        Object resolved = registry.findById(id);
        if (resolved != null) {
            return resolved;
        }
        throw new PropertyEvaluationException(this, "was unable to find bean by id: " + id);
    }

    private Object getByType(Class<?> type) throws PropertyEvaluationException {
        Object resolved = registry.findByType(type);
        if (resolved != null) {
            return resolved;
        }
        throw new PropertyEvaluationException(this, "was unable to find bean by type: " + type);
    }

    private <T> T getByType(Class<?> beanType, Class<T> collectionType) throws PropertyEvaluationException {
        try {
            List<?> beans = registry.findOfType(beanType);
            return Beans.getCollectionInstance(beans, collectionType);
        } catch (InstantiationException e) {
            throw new PropertyEvaluationException(this, "can't instantiate collection of type: " + collectionType, e);
        }
    }

    @Override
    protected String getDescriptor() {
        return "$inject";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        Set<Class<?>> types = new HashSet<>();
        types.add(String.class);
        types.add(Map.class);
        return types;
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Beans.Any.class);
    }
}
