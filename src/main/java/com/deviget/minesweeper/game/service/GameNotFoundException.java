package com.deviget.minesweeper.game.service;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Thrown if a {@link Game} cannot be located by its id.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a <code>GameNotFoundException</code> with the specified message.
     *
     * @param msg - the detail message.
     */
    public GameNotFoundException(String msg) {
        super(msg);
    }
}
