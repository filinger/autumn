package com.technoirarts.autumn.spel;

import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.bean.Beans;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class SpelBeanResolver implements BeanResolver {

    BeanRegistry registry;

    public SpelBeanResolver(BeanRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Object resolve(EvaluationContext context, String beanName) throws AccessException {
        Object beanValue = registry.findById(beanName);
        if (beanValue != null) {
            return beanValue;
        } else {
            try {
                return registry.findByType(Beans.forName(beanName));
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }
}
