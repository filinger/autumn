package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanValueResolver;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class PropertyMaker {

    private BeanValueResolver resolver;
    private List<PropertyEvaluator> evaluators;

    public PropertyMaker(BeanValueResolver resolver) {
        this.resolver = resolver;
        this.evaluators = getAvailableEvaluators();
    }

    private List<PropertyEvaluator> getAvailableEvaluators() {
        ArrayList<PropertyEvaluator> evaluators = new ArrayList<>();
        evaluators.add(new IntPropertyEvaluator(this));
        evaluators.add(new FloatPropertyEvaluator(this));
        evaluators.add(new BoolPropertyEvaluator(this));
        evaluators.add(new ArrayPropertyEvaluator(this));
        evaluators.add(new ConstructPropertyEvaluator(this));
        evaluators.add(new ClassPropertyEvaluator(this));
        evaluators.add(new InjectPropertyEvaluator(this, resolver));
        return evaluators;
    }

    public Object makeValue(Object property) throws PropertyEvaluationException {
        for (PropertyEvaluator evaluator : evaluators) {
            if (evaluator.canEvaluate(property)) {
                return evaluator.evaluate(property);
            }
        }
        return property;
    }
}
