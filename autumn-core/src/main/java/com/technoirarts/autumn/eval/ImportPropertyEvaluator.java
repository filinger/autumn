package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.Beans;
import com.technoirarts.autumn.bean.PackageRegistry;
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

    private final PackageRegistry packages;

    public ImportPropertyEvaluator(EvalPropertyMaker maker, PackageRegistry packages) {
        super(maker);
        this.packages = packages;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T evaluateDescriptor(Object descriptor, Map<String, Object> rest, Class<T> typeAdvice) throws PropertyEvaluationException {
        String packageName = (String) descriptor;
        try {
            packages.register(packageName);
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
