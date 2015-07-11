package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanPropertyMaker;
import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.exception.PropertyEvaluationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class EvalPropertyMaker implements BeanPropertyMaker {

    private BeanRegistry registry;
    private List<PropertyEvaluator> evaluators;

    public EvalPropertyMaker(BeanRegistry registry) {
        this.registry = registry;
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
        evaluators.add(new InjectPropertyEvaluator(this, registry));
        return evaluators;
    }

    public void add(PropertyEvaluator evaluator) {
        evaluators.add(evaluator);
    }

    @Override
    public Object make(Object property) throws PropertyEvaluationException {
        for (PropertyEvaluator evaluator : evaluators) {
            if (evaluator.canEvaluate(property)) {
                return evaluator.evaluate(property);
            }
        }
        return property;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T make(Object property, Class<T> type) throws PropertyEvaluationException {
        for (PropertyEvaluator evaluator : evaluators) {
            if (evaluator.canEvaluate(property)) {
                return evaluator.evaluate(property, type);
            }
        }
        return (T) property; // Just try to cast it if nothing else worked.
    }
}
