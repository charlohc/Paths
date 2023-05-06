package edu.ntnu.paths.Exceptions;
public class InvalidFilePathException extends Exception {

    public InvalidFilePathException(String message) {
        super(message);
    }

    public void checkFilePathEnding(String filePath) throws InvalidFilePathException {
        if (!filePath.endsWith(".paths")) {
            throw new InvalidFilePathException("Invalid file path - file name must end with '.paths'");
        }
    }
}
