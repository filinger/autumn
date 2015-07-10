package com.technoirarts.autumn.bean;

import java.beans.ConstructorProperties;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class ImmutableTestBean implements TestBean {

    private final String name;
    private final Integer age;
    private final TestBean relative;

    @ConstructorProperties({"name", "age", "relative"})
    public ImmutableTestBean(String name, Integer age, TestBean relative) {
        this.name = name;
        this.age = age;
        this.relative = relative;
    }

    @Override
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public TestBean getRelative() {
        return relative;
    }
}
