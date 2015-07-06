package com.technoirarts.autumn.exception;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/2/2015
 * @since 1.0
 */
public class BeanNotFoundException extends ContextLoadException {

    public BeanNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public BeanNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
