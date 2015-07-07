package com.technoirarts.autumn;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/7/2015
 * @since 1.0
 */
public interface NodeReader {

    /**
     * Returns next node from the configuration.
     *
     * @return next node or null if nothing is left
     */
    Object next();
}
