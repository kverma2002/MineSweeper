package com.example.gridlayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.example.gridlayout.MinesweeperBoard;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MinesweeperBoard minesweeperBoard;
    private TextView timerTextView;
    private TextView mineCounterTextView;
    private Button newGameButton;
    private boolean gameInProgress;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int secondsElapsed;


    // CAN CHANGE VALUES BASED ON GAME RESTRAINTS
    private static final int COLUMN_COUNT = 10;
    private static final int ROW_COUNT = 12;
    private static final int MINE_COUNT = 12;

    // HOLD UI COMPONENTS
    private ArrayList<TextView> cell_tvs;

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cell_tvs = new ArrayList<TextView>();

        // Link UI elements to Java variables
        minesweeperBoard = new MinesweeperBoard(ROW_COUNT, COLUMN_COUNT, MINE_COUNT); // Example board size and mine count
        timerTextView = findViewById(R.id.timerTextView);
        mineCounterTextView = findViewById(R.id.mineCounterTextView);
        newGameButton = findViewById(R.id.newGameButton);
        gameInProgress = false;

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j=0; j < COLUMN_COUNT; j++) {
                TextView tv = new TextView(this);
                tv.setHeight( dpToPixel(30) );
                tv.setWidth( dpToPixel(30) );
                tv.setTextSize( 10 );//dpToPixel(32) );
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundColor(Color.GRAY);
                tv.setOnClickListener(this::onClickTV);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setMargins(dpToPixel(2), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);

                grid.addView(tv, lp);

                cell_tvs.add(tv);
            }
        }

//        updateUI();
    }

    private int findIndexOfCellTextView(TextView tv) {
        for (int n=0; n<cell_tvs.size(); n++) {
            if (cell_tvs.get(n) == tv)
                return n;
        }
        return -1;
    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        int n = findIndexOfCellTextView(tv);
        int i = n/COLUMN_COUNT;
        int j = n%COLUMN_COUNT;
        tv.setText(String.valueOf(i)+String.valueOf(j));
        if (tv.getCurrentTextColor() == Color.GRAY) {
            tv.setTextColor(Color.GREEN);
            tv.setBackgroundColor(Color.parseColor("lime"));
        }else {
            tv.setTextColor(Color.GRAY);
            tv.setBackgroundColor(Color.LTGRAY);
        }
    }



//    // Start a new game or reset the game state
//    private void startNewGame() {
//        minesweeperBoard = new MinesweeperBoard(8, 8, 10);
//        secondsElapsed = 0;
//        gameInProgress = true;
//        updateUI();
//
//        // Start the timer
////        startTimer();
//    }

//    // Handle cell click based on game state
//    private void handleCellClick(int row, int col) {
//        if (!minesweeperBoard.isCellRevealed(row, col)) {
//            minesweeperBoard.revealCell(row, col);
//
//            if (minesweeperBoard.isGameOver()) {
//                // Handle game over
//                gameInProgress = false;
//                // Show game over message or dialog
//            } else {
//                // Continue the game
//                updateUI();
//            }
//        }
//    }

    // Update the UI elements based on the game state
//    private void updateUI() {
//        // Update cell buttons (reveal or flag cells)
//        for (int i = 0; i < minesweeperBoard.getRows(); i++) {
//            for (int j = 0; j < minesweeperBoard.getCols(); j++) {
//                if (minesweeperBoard.isCellRevealed(i, j)) {
//                    cellButtons[i][j].setText(String.valueOf(minesweeperBoard.getCellValue(i, j)));
//                } else {
//                    // Customize button appearance for unrevealed cells
//                }
//            }
//        }
//        // Update timer and mine counter
//        timerTextView.setText(String.format(getString(R.string.timer_format), secondsElapsed));
//        mineCounterTextView.setText(String.format(getString(R.string.mine_counter_format), minesweeperBoard.getMinesRemaining()));
//    }

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
