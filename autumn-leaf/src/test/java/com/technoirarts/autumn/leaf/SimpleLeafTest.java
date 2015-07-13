package com.technoirarts.autumn.leaf;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Filinger
 * @author $Author$ (current maintainer)
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class SimpleLeafTest {

    private static final String LEAF_CONFIG = "simple-test.leaf";

    private LeafReader leafReader;

    @Before
    public void prepareLeafReader() {
        InputStream leafStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(LEAF_CONFIG);
        leafReader = new LeafReader(new InputStreamReader(leafStream));
    }

    @Test
    public void testLeaf() {
    }
}
