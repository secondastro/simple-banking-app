package com.skypro.bankingapp.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

}
