package com.technoirarts.autumn.exception;

/**
 * @author Nilera
 * @author Nilera (current maintainer)
 * @version 03.07.2015
 * @since 1.0
 */
public class BeanConstructionException extends ContextLoadException {

    public BeanConstructionException(String detailMessage) {
        super(detailMessage);
    }

    public BeanConstructionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
