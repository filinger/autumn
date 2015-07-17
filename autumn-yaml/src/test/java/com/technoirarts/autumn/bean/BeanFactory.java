package com.technoirarts.autumn.bean;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/17/2015
 * @since 1.0
 */
public class BeanFactory {

    private String beansName;

    public TestBean getImmutableBean() {
        return new ImmutableTestBean(beansName, null, null);
    }

    public TestBean getSimpleBean() {
        SimpleTestBean testBean = new SimpleTestBean();
        testBean.setName(beansName);
        return testBean;
    }

    public void setBeansName(String beansName) {
        this.beansName = beansName;
    }
}
