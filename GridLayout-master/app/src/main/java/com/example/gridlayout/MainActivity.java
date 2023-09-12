package com.example.gridlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gridlayout.MinesweeperBoard;

public class MainActivity extends AppCompatActivity {
    private MinesweeperBoard minesweeperBoard;
    private TextView timerTextView;
    private TextView mineCounterTextView;
    private Button newGameButton;
    private boolean gameInProgress;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int secondsElapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI elements to Java variables
        minesweeperBoard = new MinesweeperBoard(12, 10, 4); // Example board size and mine count
        timerTextView = findViewById(R.id.timerTextView);
        mineCounterTextView = findViewById(R.id.mineCounterTextView);
        newGameButton = findViewById(R.id.newGameButton);
        gameInProgress = false;

        updateUI();
    }



    // Start a new game or reset the game state
    private void startNewGame() {
        minesweeperBoard = new MinesweeperBoard(8, 8, 10);
        secondsElapsed = 0;
        gameInProgress = true;
        updateUI();

        // Start the timer
//        startTimer();
    }

    // Handle cell click based on game state
    private void handleCellClick(int row, int col) {
        if (!minesweeperBoard.isCellRevealed(row, col)) {
            minesweeperBoard.revealCell(row, col);

            if (minesweeperBoard.isGameOver()) {
                // Handle game over
                gameInProgress = false;
                // Show game over message or dialog
            } else {
                // Continue the game
                updateUI();
            }
        }
    }

    // Update the UI elements based on the game state
    private void updateUI() {
        // Update cell buttons (reveal or flag cells)
        for (int i = 0; i < minesweeperBoard.getRows(); i++) {
            for (int j = 0; j < minesweeperBoard.getCols(); j++) {
                if (minesweeperBoard.isCellRevealed(i, j)) {
                    cellButtons[i][j].setText(String.valueOf(minesweeperBoard.getCellValue(i, j)));
                } else {
                    // Customize button appearance for unrevealed cells
                }
            }
        }
        // Update timer and mine counter
        timerTextView.setText(String.format(getString(R.string.timer_format), secondsElapsed));
        mineCounterTextView.setText(String.format(getString(R.string.mine_counter_format), minesweeperBoard.getMinesRemaining()));
    }

    // Start the timer
//    private void startTimer() {
//        timerHandler = new Handler();
//        timerRunnable = new Runnable() {
//            @Override
//            public void run() {
//                secondsElapsed++;
//                timerTextView.setText(String.format(getString(R.string.timer_format), secondsElapsed));
//                timerHandler.postDelayed(this, 1000);
//            }
//        };
//        timerHandler.postDelayed(timerRunnable, 1000);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the timer callbacks to prevent memory leaks
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
}
