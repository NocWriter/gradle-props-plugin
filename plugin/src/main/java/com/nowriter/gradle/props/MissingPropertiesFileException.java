package com.nowriter.gradle.props;

/**
 * This exception indicates that a mandatory/required properties file is missing.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class MissingPropertiesFileException extends RuntimeException {

    public MissingPropertiesFileException() {
    }

    public MissingPropertiesFileException(String message) {
        super(message);
    }
}
