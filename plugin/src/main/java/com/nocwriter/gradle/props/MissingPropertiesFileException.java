package com.nocwriter.gradle.props;

/**
 * This exception indicates that a mandatory/required properties file is missing.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class MissingPropertiesFileException extends RuntimeException {

    /**
     * Class constructor.
     */
    public MissingPropertiesFileException() {
    }

    /**
     * Class constructor.
     *
     * @param message Error message.
     */
    public MissingPropertiesFileException(String message) {
        super(message);
    }
}
