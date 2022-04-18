package com.nowriter.gradle.props;

/**
 * This exception indicates failure to read a properties file due to I/O error.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class PropertiesIOException extends RuntimeException {

    public PropertiesIOException() {
    }

    public PropertiesIOException(String message) {
        super(message);
    }

    public PropertiesIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesIOException(Throwable cause) {
        super(cause);
    }

    public PropertiesIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
