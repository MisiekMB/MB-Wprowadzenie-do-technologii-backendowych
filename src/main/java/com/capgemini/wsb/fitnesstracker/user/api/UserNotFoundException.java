package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Wyjątek rzucany, gdy użytkownik nie zostanie znaleziony.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
