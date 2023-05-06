package edu.ntnu.paths.Exceptions;

import java.io.File;

public class EmptyFileException extends Exception {
    public EmptyFileException(String errorMessage) {
        super(errorMessage);
    }
    public void checkFileContent(File file) throws EmptyFileException {
        if (file.length() == 0) {
            throw new EmptyFileException("File cannot be empty");
        }
    }

}
