package com.deviget.minesweeper.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity class representing a mine sweeper game as it will be persisted in the underline data source.
 *
 * @author david.rios
 */
@Entity
public class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Long timer = 0l;

    private Integer size;

    private Integer mines;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Row> rows;

    @Column(name = "remaining_cells")
    private Integer remainingCells;

    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.IN_GAME;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Game() {
    }

    public Game(Long id, String username, Long timer, Integer size, Integer mines, List<Row> rows, Integer remainingCells,
            GameStatus status, LocalDateTime lastUpdated) {
        this.id = id;
        this.username = username;
        this.timer = timer;
        this.size = size;
        this.mines = mines;
        this.rows = rows;
        this.remainingCells = remainingCells;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public void addRow(Row row) {
        if (row != null) {
            if (rows == null) {
                rows = new ArrayList<>();
            }
            rows.add(row);
            row.setGame(this);
        }
    }

    public void setRowsFromCellArray(Cell[][] cellsArray) {
        if (this.rows != null) {
            this.rows.clear();
        }
        for (int i = 0; i < cellsArray.length; i++) {
            Row row = new Row();
            for (int j = 0; j < cellsArray[i].length; j++) {
                Cell cell = cellsArray[i][j];
                row.addCell(cell);
            }
            this.addRow(row);
        }
        for (Row row : this.getRows()) {
            logger.debug(">>> row:" + row);
        }
    }

    public Cell[][] getCellArrayFromRows() {
        Cell[][] cellsArray = new Cell[rows.size()][rows.size()];
        int i = 0;
        for (Row row : rows) {
            int j = 0;
            for (Cell cell : row.getCells()) {
                cellsArray[i][j] = cell;
                j++;
            }
            i++;
        }
        return cellsArray;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Integer getRemainingCells() {
        return remainingCells;
    }

    public void setRemainingCells(Integer remainingCells) {
        this.remainingCells = remainingCells;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setMines(Integer mines) {
        this.mines = mines;
    }
}
