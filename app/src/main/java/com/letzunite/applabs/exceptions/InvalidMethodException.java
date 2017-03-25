package com.letzunite.applabs.exceptions;

/**
 * This exception is thrown when an unknown Method type is requested to the ConnectionFactory.
 *
 * @author Akash Patra
 */
public class InvalidMethodException extends Exception {
    private static final long serialVersionUID = 7374739211962780312L;

    public InvalidMethodException() {
        super("The requested Method type is invalid.");
    }

    public InvalidMethodException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidMethodException(Throwable throwable) {
        super(throwable);
    }

    public InvalidMethodException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
