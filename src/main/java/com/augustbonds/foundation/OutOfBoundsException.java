package com.augustbonds.foundation;

/**
 * An exception that signifies an attempt to access elements outside of the collection.
 */
class OutOfBoundsException extends RuntimeException {

    OutOfBoundsException() {
    }

    OutOfBoundsException(String message) {
        super(message);
    }
}
