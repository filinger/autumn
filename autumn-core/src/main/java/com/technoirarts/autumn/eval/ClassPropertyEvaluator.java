package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.ClassNameResolver;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class ClassPropertyEvaluator extends DescriptorPropertyEvaluator {

    private final ClassNameResolver resolver;

    public ClassPropertyEvaluator(EvalPropertyMaker maker, ClassNameResolver resolver) {
        super(maker);
        this.resolver = resolver;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        try {
            return (T) resolver.findClass((String) descriptor);
        } catch (ClassNotFoundException e) {
            throw new PropertyEvaluationException(this, "cannot find class", e);
        }
    }

    @Override
    protected String getDescriptor() {
        return "$class";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Class.class);
    }
}
