package com.technoirarts.autumn.bean;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/2/2015
 * @since 1.0
 */
public class Bean<T> {

    private final String id;
    private final Class<T> type;
    private final T value;

    public Bean(String id, Class<T> type, T value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

    public String getId() {
        return id;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValue() {
        return value;
    }
}
