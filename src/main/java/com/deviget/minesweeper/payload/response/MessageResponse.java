package com.deviget.minesweeper.payload.response;

/**
 * Payload class used for generic string responses.
 * 
 * @author david.rios
 */
public class MessageResponse {

    private String message;

    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
