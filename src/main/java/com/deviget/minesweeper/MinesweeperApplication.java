package com.deviget.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RESTful API for Minesweeper game.
 * <p>
 * Current, the following functionalities are supported:
 * <ol>
 * <li>Start a new game and preserve/resume the old ones</li>
 * <li>Ability to select the game parameters: number of rows, columns, and mines</li>
 * <li>Ability to support multiple users/accounts</li>
 * </ol>
 * <p>
 * The game board is represented as a list of integers. On new games, each cell is populated with one of the following
 * values:
 * <ol>
 * <li>A value between 1 and 8 (number of adjacent mines)</li>
 * <li>9 (MINED_CELL)</li>
 * <li>10 (COVERED_CELL)</li>
 * </ol>
 * Consumers of this API are responsible for updating the timer and game board.
 * 
 * @author david.rios
 */
@SpringBootApplication
public class MinesweeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperApplication.class, args);
    }
}
