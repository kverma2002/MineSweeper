package com.example.gridlayout;
import java.util.Random;

public class MinesweeperBoard {
    private int rows;
    private int cols;
    private int numMines;
    private Tile[][] board; // Represents the game board
    private boolean[][] revealed; // Keeps track of which cells are revealed
    private boolean gameOver;

    public MinesweeperBoard(int rows, int cols, int numMines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        this.board = new Tile[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.gameOver = false;
        generateBoard();
    }

    private void generateBoard() {
        // Initialize the board with zeros (empty cells)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Tile();
            }
        }

        // Randomly place mines on the board
        Random rand = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);

            if (!board[randRow][randCol].isMine()) {
                board[randRow][randCol].setMine(true); // -1 represents a mine
                minesPlaced++;
            }
        }

        // Calculate the number of adjacent mines for each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!board[i][j].isMine()) {
                    board[i][j].setSurrondingMines(countAdjacentMines(i, j));
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols && board[i][j].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }


    /*
    FLAG CELL FOR LONG CLICK TO FLAG MINE CELL
    - Dont have to check if mine cell
    - Only have to check to see if you can click it meaning it is not already revealed
    - IF flagged set to not flagged
    - If not flagged set ot flagged
    */
    public int flagCell(int row, int col) {
        if (!board[row][col].isCanClick()) {
            return -1;
        }
        if (!board[row][col].isFlagged()) {
            board[row][col].setFlagged(true);
            return 1;
        } else {
            board[row][col].setFlagged(false);
            return 0;
        }
    }

    public int revealCell(int row, int col) {
        if (gameOver || !board[row][col].isCanClick()) {
            return 0;
        }
        board[row][col].setCanClick(false);

        if (board[row][col].isMine()) {
            // Game over: the player has hit a mine
            gameOver = true;
            return -1;
        } else {
            board[row][col].setCovered(false);

            if (board[row][col].getSurrondingMines() == 0) {
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i >= 0 && i < rows && j >= 0 && j < cols) {
                            revealCell(i, j);
                        }
                    }
                }
            }

            checkWin();
            return 1;
            // If the cell is empty, recursively reveal adjacent cells

        }
    }

    private void checkWin() {
        
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isCellRevealed(int row, int col) {
        return board[row][col].isCovered();
    }

    public int getCellValue(int row, int col) {
        if (board[row][col].isMine()) { return -1;}
        else {return board[row][col].getSurrondingMines(); }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isCellFlagged(int i, int j) {
        return board[i][j].isFlagged();
    }
}
