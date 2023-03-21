package com.github.yildizmy.exception;

/**
 * Custom exception class used for when there is no record for the given filter parameters
 */
public class NoSuchElementFoundException extends RuntimeException {

    public NoSuchElementFoundException() {
        super();
    }

    public NoSuchElementFoundException(String message) {
        super(message);
    }

    public NoSuchElementFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
