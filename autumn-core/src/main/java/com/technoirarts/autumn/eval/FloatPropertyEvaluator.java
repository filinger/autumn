package com.technoirarts.autumn.eval;

import java.util.Map;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class FloatPropertyEvaluator extends DescriptorPropertyEvaluator {

    public FloatPropertyEvaluator(PropertyMaker maker) {
        super(maker);
    }

    @Override
    protected String getDescriptor() {
        return "$float";
    }

    @Override
    protected Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) {
        return Float.parseFloat((String) descriptor);
    }
}
