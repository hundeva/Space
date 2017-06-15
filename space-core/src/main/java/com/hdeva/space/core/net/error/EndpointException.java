package com.hdeva.space.core.net.error;

public class EndpointException extends Exception {

    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(Throwable cause) {
        super(cause);
    }

}
