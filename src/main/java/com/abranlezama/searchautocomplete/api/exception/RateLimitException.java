package com.abranlezama.searchautocomplete.api.exception;

public class RateLimitException extends RuntimeException{

    public RateLimitException(String message) {
        super(message);
    }
}
