package com.technoirarts.autumn.eval;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public interface PropertyEvaluator {

    boolean canEvaluate(Object property);

    Object evaluate(Object property);
}
