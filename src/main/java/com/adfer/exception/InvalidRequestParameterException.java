package com.adfer.exception;

/**
 * Created by adrianferenc on 18.12.2016.
 */
public class InvalidRequestParameterException extends RuntimeException {
    public InvalidRequestParameterException(String message) {
        super(message);
    }
}
