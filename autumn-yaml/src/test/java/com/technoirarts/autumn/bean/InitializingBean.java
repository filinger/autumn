package com.technoirarts.autumn.bean;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/16/2015
 * @since 1.0
 */
public class InitializingBean implements TestBean {

    private String name;
    private TestBean beanRef;

    @Override
    public String getName() {
        return name;
    }

    public TestBean getBeanRef() {
        return beanRef;
    }

    public void doInit() {
        name = beanRef.getName();
    }
}
