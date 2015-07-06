package com.technoirarts.autumn;

import java.io.Reader;
import java.util.List;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public interface ApplicationContext {

    /**
     * Sets the YAML data source for this context.
     *
     * @param resourceReader reader which reads YAML resource.
     */
    void setResourceReader(Reader resourceReader);

    /**
     * Loads all documents described in the YAML config.
     */
    void loadAll();

    /**
     * Loads only sub-document from the YAML config.
     */
    void loadNext();

    /**
     * Returns value of bean with specified id. Null if nothing is found.
     *
     * @param id id of the bean
     * @return bean value or null if nothing is found
     */
    Object getBean(String id);

    /**
     * Returns value of bean with specified type. Null if nothing is found.
     *
     * @param type type of the bean
     * @param <T>  value type
     * @return bean value or null if nothing is found
     */
    <T> T getBean(Class<T> type);

    /**
     * Returns list of bean values with specified type. Null if nothing is found.
     *
     * @param type type of the beans
     * @param <T>  value type
     * @return list of bean values or null if nothing is found
     */
    <T> List<T> getBeans(Class<T> type);
}
