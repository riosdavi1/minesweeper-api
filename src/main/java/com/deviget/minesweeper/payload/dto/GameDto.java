package com.deviget.minesweeper.payload.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.model.GameStatus;

/**
 * Payload class used for new games requests.
 *
 * @author david.rios
 */
public class GameDto {

    private static final Logger logger = LoggerFactory.getLogger(GameDto.class);

    private Long id;

    private String username;

    private Long timer;

    @NotNull
    @Max(1000)
    private Integer size;

    @NotNull
    @Max(1000)
    private Integer mines;

    @NotNull
    private Cell[][] cells;

    @NotNull
    private Integer remainingCells;

    @NotNull
    private String status;

    private LocalDateTime lastUpdated;

    public GameDto() {
    }

    public GameDto(Long id, String username, Long timer, Integer size, Integer mines, Cell[][] cells, Integer remainingCells, String status,
            LocalDateTime lastUpdated) {
        this.id = id;
        this.username = username;
        this.timer = timer;
        this.size = size;
        this.mines = mines;
        this.cells = cells;
        this.remainingCells = remainingCells;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public static Game toGame(GameDto gameDto) {
        logger.debug(">>> cells.lenght:" + gameDto.getCells().length);
        //@formatter:off
        Game game = new Game(gameDto.getId(),
                gameDto.getUsername(),
                gameDto.getTimer(),
                gameDto.getSize(),
                gameDto.getMines(),
                null,
                gameDto.getRemainingCells(),
                GameStatus.valueOf(gameDto.getStatus()),
                gameDto.getLastUpdated());
        //@formatter:on
        game.setRowsFromCellArray(gameDto.getCells());
        return game;
    }

    public static GameDto fromGame(Game game, boolean fullCopy) {
        //@formatter:off
        GameDto gameDto = new GameDto(game.getId(),
                game.getUsername(),
                game.getTimer(),
                game.getSize(),
                game.getMines(),
                fullCopy ? game.getCellArrayFromRows() : null,
                game.getRemainingCells(),
                game.getStatus().name(),
                game.getLastUpdated());
        //@formatter:on
        return gameDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getMines() {
        return mines;
    }

    public void setMines(Integer mines) {
        this.mines = mines;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Integer getRemainingCells() {
        return remainingCells;
    }

    public void setRemainingCells(Integer remainingCells) {
        this.remainingCells = remainingCells;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                .append("username", username)
                .append("timer", timer)
                .append("size", size)
                .append("mines", mines)
                .append("remainingCells", remainingCells)
                .append("status", status)
                .append("lastUpdated", lastUpdated)
                .toString();
    }
}
