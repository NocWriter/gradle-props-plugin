package com.nowriter;

import java.net.URL;

/**
 * Utilities class for test suites.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class Utils {

    /**
     * Resolve a classpath resource into an absolute path.
     *
     * @param classpathResource Classpath resource to resolve.
     * @return Absolute path.
     * @throws IllegalArgumentException If resource does not exist.
     */
    public static String getAbsolutePath(String classpathResource) throws IllegalArgumentException {
        URL url = Utils.class.getResource(classpathResource);
        if (url == null) {
            throw new IllegalArgumentException("Missing classpath resource: " + classpathResource);
        }
        return url.getPath();
    }
}
