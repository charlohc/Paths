package edu.ntnu.paths.Exceptions;

/**
 * Exception thrown when a story already exists.
 */
public class StoryExistException extends Exception {

    /**
     * Constructs a StoryExistException with the specified error message.
     *
     * @param errorMessage The error message describing the exception.
     */
    public StoryExistException(String errorMessage) {
        super(errorMessage);
    }
}
