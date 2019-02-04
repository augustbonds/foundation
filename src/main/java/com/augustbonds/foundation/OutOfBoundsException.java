package com.augustbonds.foundation;

/**
 * An exception that signifies an attempt to access elements outside of the collection.
 */
class OutOfBoundsException extends RuntimeException {

    public OutOfBoundsException() {
    }

    public OutOfBoundsException(String message) {
        super(message);
    }
}
