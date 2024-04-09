package com.daniellsantiago.spaceprobe.lib.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
