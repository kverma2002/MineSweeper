package com.example.gridlayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;


public class Tile {

    // Assign Variables
    private boolean isCovered;
    private boolean isMine;
    private boolean isFlagged;
    private boolean canClick;
    private int surrondingMines;

    public Tile() {
        setDefaults();
    }

    public Tile(boolean isCovered, boolean isMine, boolean isFlagged, boolean canClick, int surrondingMines) {
        this.isCovered = isCovered;
        this.isMine = isMine;
        this.isFlagged = isFlagged;
        this.canClick = canClick;
        this.surrondingMines = surrondingMines;
    }



    /////////////////////////////
    /// Setters and Getters ////
    ///////////////////////////

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public int getSurrondingMines() {
        return surrondingMines;
    }

    public void setSurrondingMines(int surrondingMines) {
        this.surrondingMines = surrondingMines;
    }


    public void setDefaults() {
        isCovered = true;
        isMine = false;
        isFlagged = false;
        canClick = true;
        surrondingMines = 0;
    }

    /////////////////////////////
    /// Setters and Getters ////
    ///////////////////////////

    // Create
}
