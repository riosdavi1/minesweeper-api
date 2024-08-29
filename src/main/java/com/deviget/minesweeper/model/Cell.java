package com.deviget.minesweeper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity class representing a cell in a mine sweeper game board, as it will be persisted in the underline data source.
 *
 * @author david.rios
 */
@Entity
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Row row;

    private Integer y;

    private Integer x;

    private String status;

    private boolean mine;

    @Column(name = "proximity_mines")
    private Integer proximityMines;

    public Cell() {
    }

    public Cell(Integer y, Integer x, String status, boolean mine, Integer proximityMines) {
        this.y = y;
        this.x = x;
        this.status = status;
        this.mine = mine;
        this.proximityMines = proximityMines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public Integer getProximityMines() {
        return proximityMines;
    }

    public void setProximityMines(Integer proximityMines) {
        this.proximityMines = proximityMines;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status)
                .append("mine", mine)
                .append("y", y)
                .append("x", x)
                .toString();
    }
}
