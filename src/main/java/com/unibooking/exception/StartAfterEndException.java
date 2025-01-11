package com.unibooking.exception;

public class StartAfterEndException extends RuntimeException {
    public StartAfterEndException(String message) {
        super(message);
    }

    public StartAfterEndException(String message, Throwable cause) {
        super(message, cause);
    }
}
