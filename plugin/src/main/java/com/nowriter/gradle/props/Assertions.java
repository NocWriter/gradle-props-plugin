package com.nowriter.gradle.props;

/**
 * Collection of assertion utilities.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class Assertions {

    /**
     * Assert that a given <i>obj</i> is not {@code null}. If <i>obj</i> is {@code null} - this call will raise
     * {@link IllegalArgumentException}.
     *
     * @param obj     Object to assert.
     * @param message Error message in case an exception is thrown.
     * @throws IllegalArgumentException If <i>obj</i> is {@code null}.
     */
    public static void notNull(Object obj, String message) throws IllegalArgumentException {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
