package com.deviget.minesweeper.game.service;

/**
 * Thrown if a {@link Game} is not available for the current user.
 */
public class GameNotAvailableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>GameNotAvailableException</code> with the specified message.
     *
     * @param msg - the detail message.
     */
    public GameNotAvailableException(String msg) {
        super(msg);
    }
}
