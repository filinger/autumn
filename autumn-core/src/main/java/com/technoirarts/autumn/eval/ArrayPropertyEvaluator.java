package com.technoirarts.autumn.eval;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class ArrayPropertyEvaluator extends BasicPropertyEvaluator {

    public ArrayPropertyEvaluator(PropertyMaker maker) {
        super(maker);
    }

    @Override
    public boolean canEvaluate(Object property) {
        return property instanceof Collection;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object checkedEvaluate(Object property) {
        Collection properties = (Collection) property;
        ArrayList evaluated = new ArrayList(properties.size());
        for (Object prop : properties) {
            evaluated.add(maker.makeValue(prop));
        }
        return evaluated;
    }
}
