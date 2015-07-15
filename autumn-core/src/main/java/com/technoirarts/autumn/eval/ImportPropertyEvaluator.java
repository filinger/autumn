package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.Beans;
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
public class ImportPropertyEvaluator extends DescriptorPropertyEvaluator {

    private final ClassNameResolver classResolver;

    public ImportPropertyEvaluator(EvalPropertyMaker maker, ClassNameResolver classResolver) {
        super(maker);
        this.classResolver = classResolver;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice, Class<?>... typeParameters) throws PropertyEvaluationException {
        String packageName = (String) descriptor;
        try {
            classResolver.addPackage(packageName);
        } catch (IllegalArgumentException e) {
            throw new PropertyEvaluationException(this, "can't find package with name: " + packageName, e);
        }
        return (T) new Beans.Null();
    }

    @Override
    protected String getDescriptor() {
        return "$import";
    }

    @Override
    protected Set<Class<?>> getDescriptorTypes() {
        return Collections.<Class<?>>singleton(String.class);
    }

    @Override
    protected Set<Class<?>> getReturnTypes() {
        return Collections.<Class<?>>singleton(Beans.Null.class);
    }
}
