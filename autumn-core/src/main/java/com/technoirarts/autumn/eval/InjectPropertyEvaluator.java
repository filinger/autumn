package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanValueResolver;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.List;
import java.util.Map;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class InjectPropertyEvaluator extends DescriptorPropertyEvaluator {

    private BeanValueResolver resolver;

    public InjectPropertyEvaluator(EvalPropertyMaker maker, BeanValueResolver resolver) {
        super(maker);
        this.resolver = resolver;
    }

    @Override
    protected String getDescriptor() {
        return "$inject";
    }

    @Override
    protected Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) throws PropertyEvaluationException {
        String idOrType = (String) descriptor;
        Object resolved = resolver.getValueById(idOrType);
        if (resolved != null) {
            return resolved;
        }
        resolved = resolver.getValueByType(idOrType);
        if (resolved != null) {
            return resolved;
        }
        throw new PropertyEvaluationException(this, "was unable to find requested bean: " + descriptor);
    }

    @Override
    public boolean canEvaluate(Object property, Class<?> typeAdvice) {
        return canEvaluate(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice) throws PropertyEvaluationException {
        if (typeAdvice.isAssignableFrom(List.class)) {
            String className = (String) descriptor;
            return (T) resolver.getValuesByType(className);
        } else if (descriptor == null || String.class.cast(descriptor).isEmpty()) {
            return resolver.getValueByType(typeAdvice);
        } else {
            return (T) evaluateDescriptor(descriptor, rest);
        }
    }
}
