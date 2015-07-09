package com.technoirarts.autumn.eval;

import java.util.Map;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class IntPropertyEvaluator extends DescriptorPropertyEvaluator {

    public IntPropertyEvaluator(PropertyMaker maker) {
        super(maker);
    }

    @Override
    protected String getDescriptor() {
        return "$int";
    }

    @Override
    protected Object evaluateDescriptor(Object descriptor, Map<String, Object> rest) {
        return Integer.parseInt((String) descriptor);
    }
}
