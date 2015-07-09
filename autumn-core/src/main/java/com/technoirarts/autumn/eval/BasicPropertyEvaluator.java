package com.technoirarts.autumn.eval;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public abstract class BasicPropertyEvaluator implements PropertyEvaluator {

    protected final PropertyMaker maker;

    public BasicPropertyEvaluator(PropertyMaker maker) {
        this.maker = maker;
    }

    @Override
    public Object evaluate(Object property) {
        if (canEvaluate(property)) {
            return checkedEvaluate(property);
        }
        return property;
    }

    protected abstract Object checkedEvaluate(Object property);
}
