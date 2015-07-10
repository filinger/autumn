package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class ArrayPropertyEvaluator extends BasicPropertyEvaluator {

    public ArrayPropertyEvaluator(EvalPropertyMaker maker) {
        super(maker);
    }

    @Override
    public boolean canEvaluate(Object property) {
        return property instanceof Collection;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object checkedEvaluate(Object property) throws PropertyEvaluationException {
        Collection properties = (Collection) property;
        ArrayList evaluated = new ArrayList(properties.size());
        for (Object prop : properties) {
            evaluated.add(maker.make(prop));
        }
        return evaluated;
    }
}
