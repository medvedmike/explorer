package com.explorer.domain;

/**
 * Created by Michael on 22.07.2014.
 */
public class Message {
    private String message;
    private boolean error;

    public Message(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
