package com.technoirarts.autumn.spel;

import com.technoirarts.autumn.bean.BeanRegistry;
import com.technoirarts.autumn.bean.BeanRegistryValueResolver;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class SpelBeanResolver extends BeanRegistryValueResolver implements BeanResolver {

    public SpelBeanResolver(BeanRegistry registry) {
        super(registry);
    }

    @Override
    public Object resolve(EvaluationContext context, String beanName) throws AccessException {
        Object beanValue = getValueById(beanName);
        if (beanValue != null) {
            return beanValue;
        } else {
            return getValueByType(beanName);
        }
    }
}
