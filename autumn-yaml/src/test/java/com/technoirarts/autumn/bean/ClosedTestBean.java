package com.technoirarts.autumn.bean;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class ClosedTestBean implements TestBean {

    private String name;

    @Override
    public String getName() {
        return name;
    }
}
