package com.deviget.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity class representing a row in a mine sweeper game board, as it will be persisted in the underline data source.
 *
 * @author david.rios
 */
@Entity
public class Row {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cells;

    public Row() {
    }

    public Row(List<Cell> cells) {
        this.cells = cells;
    }

    public void addCell(Cell cell) {
        if (cell != null) {
            if (cells == null) {
                cells = new ArrayList<>();
            }
            cells.add(cell);
            cell.setRow(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cells", cells)
                .toString();
    }
}
