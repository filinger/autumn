package com.technoirarts.autumn.leaf;

import java.io.BufferedReader;
import java.io.Reader;

/**
 * @author Nilera
 * @author Nilera (current maintainer)
 * @version 13.07.2015
 * @since 1.0
 */
public class LeafReader {

    private final BufferedReader reader;

    public LeafReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }
}
