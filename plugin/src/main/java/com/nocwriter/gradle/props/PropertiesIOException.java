package com.nocwriter.gradle.props;

/**
 * This exception indicates failure to read a properties file due to I/O error.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class PropertiesIOException extends RuntimeException {

    /**
     * Class constructor.
     */
    public PropertiesIOException() {
    }

    /**
     * Class constructor.
     *
     * @param message Error message.
     */
    public PropertiesIOException(String message) {
        super(message);
    }

    /**
     * Class constructor.
     *
     * @param message Error message.
     * @param cause   The cause of this error.
     */
    public PropertiesIOException(String message, Throwable cause) {
        super(message, cause);
    }

}
