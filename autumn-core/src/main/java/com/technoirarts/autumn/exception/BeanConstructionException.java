package com.technoirarts.autumn.exception;

/**
 * @author i.samborskiy
 * @date 03.07.2015
 */
public class BeanConstructionException extends ContextLoadException {

    public BeanConstructionException(String detailMessage) {
        super(detailMessage);
    }

    public BeanConstructionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
