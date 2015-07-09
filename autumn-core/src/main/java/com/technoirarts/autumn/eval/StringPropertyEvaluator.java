package com.technoirarts.autumn.eval;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class StringPropertyEvaluator extends BasicPropertyEvaluator {

    public StringPropertyEvaluator(PropertyMaker maker) {
        super(maker);
    }

    @Override
    public boolean canEvaluate(Object property) {
        return property instanceof String;
    }

    @Override
    protected Object checkedEvaluate(Object property) {
        return property;
    }
}
