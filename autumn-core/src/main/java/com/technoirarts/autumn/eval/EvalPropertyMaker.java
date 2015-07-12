package com.technoirarts.autumn.eval;

import com.technoirarts.autumn.bean.BeanPropertyMaker;
import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.bean.PackageRegistry;
import com.technoirarts.autumn.bean.SetPackageRegistry;
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

    private final BeanRegistry beans;
    private final PackageRegistry packages;
    private final List<PropertyEvaluator> evaluators;

    public EvalPropertyMaker(BeanRegistry beans) {
        this.beans = beans;
        this.packages = new SetPackageRegistry();
        this.evaluators = getAvailableEvaluators();
    }

    private List<PropertyEvaluator> getAvailableEvaluators() {
        ArrayList<PropertyEvaluator> evaluators = new ArrayList<>();
        evaluators.add(new IntPropertyEvaluator(this));
        evaluators.add(new FloatPropertyEvaluator(this));
        evaluators.add(new BoolPropertyEvaluator(this));
        evaluators.add(new CollectionPropertyEvaluator(this));
        evaluators.add(new BeanPropertyEvaluator(this));
        evaluators.add(new ImportPropertyEvaluator(this, packages));
        evaluators.add(new ClassPropertyEvaluator(this, packages));
        evaluators.add(new NewPropertyEvaluator(this, packages));
        evaluators.add(new InjectPropertyEvaluator(this, beans, packages));
        return evaluators;
    }

    public void add(PropertyEvaluator evaluator) {
        evaluators.add(evaluator);
    }

    @Override
    public Object make(Object property) throws PropertyEvaluationException {
        for (PropertyEvaluator evaluator : evaluators) {
            if (evaluator.canEvaluate(property, Object.class)) {
                return evaluator.evaluate(property, Object.class);
            }
        }
        return property;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T make(Object property, Class<T> type) throws PropertyEvaluationException {
        for (PropertyEvaluator evaluator : evaluators) {
            if (evaluator.canEvaluate(property, type)) {
                return evaluator.evaluate(property, type);
            }
        }
        return (T) property; // Just try to cast it if nothing else worked.
    }
}
