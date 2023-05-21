package edu.ntnu.paths.Exceptions;

/**
 * Exception thrown when encountering invalid file data.
 */
public class InvalidFileDataException extends Exception {

    /**
     * Constructs an InvalidFileDataException with the specified error message.
     *
     * @param errorMessage The error message describing the exception.
     */
    public InvalidFileDataException(String errorMessage) {
        super(errorMessage);
    }
}
