package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

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

    private BeanRegistry registry;

    public InjectPropertyEvaluator(EvalPropertyMaker maker, BeanRegistry registry) {
        super(maker);
        this.registry = registry;
    }

    @Override
    protected String getDescriptor() {
        return "$inject";
    }

    @Override
    protected Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) throws PropertyEvaluationException {
        String idOrType = (String) descriptor;
        Object resolved = registry.findById(idOrType);
        if (resolved != null) {
            return resolved;
        }
        resolved = registry.findByType(idOrType);
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
            return (T) registry.findOfType((String) descriptor);
        } else if (typeAdvice.isAssignableFrom(Set.class)) {
            return (T) new HashSet<>(registry.findOfType((String) descriptor));
        } else if (descriptor == null || String.class.cast(descriptor).isEmpty()) {
            return registry.findByType(typeAdvice);
        } else {
            return (T) evaluateDescriptor(descriptor, rest);
        }
    }
}
