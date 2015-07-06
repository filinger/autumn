package com.technoirarts.autumn.exception;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/2/2015
 * @since 1.0
 */
public class ContextLoadException extends RuntimeException {

    public ContextLoadException(String detailMessage) {
        super(detailMessage);
    }

    public ContextLoadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
