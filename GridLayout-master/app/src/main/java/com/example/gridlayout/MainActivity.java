package com.example.gridlayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.example.gridlayout.MinesweeperBoard;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MinesweeperBoard minesweeperBoard;
    private TextView flagCounterTextView;
    private Button mineOrFlag;
    private ImageView imgMineOrFlag;
    private boolean gameInProgress;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int secondsElapsed;
    private boolean mining;

    private int clock = 0;
    private boolean running = false;



    // CAN CHANGE VALUES BASED ON GAME RESTRAINTS
    private static final int COLUMN_COUNT = 10;
    private static final int ROW_COUNT = 12;
    private static final int MINE_COUNT = 4;
    private int FLAG_COUNT = 4;

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
        flagCounterTextView = findViewById(R.id.flagCounterTextView);
        flagCounterTextView.setText("Flags: " + FLAG_COUNT );
        mineOrFlag = findViewById(R.id.actionButton);
        mineOrFlag.setOnClickListener(this::onClickMineOrFlag);
        imgMineOrFlag = findViewById(R.id.mineOrFlagView);
        gameInProgress = false;
        mining = true;

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j=0; j < COLUMN_COUNT; j++) {
                TextView tv = new TextView(this);
                tv.setHeight(dpToPixel(30));
                tv.setWidth(dpToPixel(30));
                tv.setTextSize(10);//dpToPixel(32) );
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundColor(Color.GRAY);
                String s = String.valueOf(minesweeperBoard.getCellValue(i,j));
                tv.setText(s);
                tv.setOnClickListener(this::onClickTV);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setMargins(dpToPixel(2), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);

                grid.addView(tv, lp);
                cell_tvs.add(tv);
            }
        }
        startTimer();
    }

    private int findIndexOfCellTextView(TextView tv) {
        for (int n=0; n<cell_tvs.size(); n++) {
            if (cell_tvs.get(n) == tv)
                return n;
        }
        return -1;
    }

    public void onClickMineOrFlag(View view) {
        if (mining) {
            imgMineOrFlag.setImageResource(R.drawable.flag);
            mineOrFlag.setText("Flag");
            mining = false;
        }
        else {
            imgMineOrFlag.setImageResource(R.drawable.pickaxe);
            mineOrFlag.setText("Mine");
            mining = true;

        }

    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        int n = findIndexOfCellTextView(tv);
        int i = n/COLUMN_COUNT;
        int j = n%COLUMN_COUNT;

        if(mining) {
            miningClick(i, j, tv);
        }
        else {
            flagClick(i,j, tv);
        }
    }

    private void flagClick(int i, int j, TextView tv) {
        int x = minesweeperBoard.flagCell(i,j);
        if ( x == 1) {
            tv.setText(R.string.flag);
            FLAG_COUNT -= 1;
            flagCounterTextView.setText("Flags: " + FLAG_COUNT);
        }
        else if ( x== 0) {
            FLAG_COUNT += 1;
            flagCounterTextView.setText("Flags: " + FLAG_COUNT);
            tv.setText("");
        }

    }

    private void miningClick(int i, int j, TextView tv) {

    }


    private void gameOver() {


    }


    // Start a new game or reset the game state
    public void startNewGame(View view) {
        minesweeperBoard = new MinesweeperBoard(ROW_COUNT, COLUMN_COUNT, MINE_COUNT);
        secondsElapsed = 0;
        gameInProgress = true;
        updateUI();

        // Start the timer
        clock = 0;
    }

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

//     Update the UI elements based on the game state
    private void updateUI() {
        // Update cell buttons (reveal or flag cells)
        for (int n=0; n<cell_tvs.size(); n++) {
            int i = n/COLUMN_COUNT;
            int j = n%COLUMN_COUNT;
            String s = String.valueOf(minesweeperBoard.getCellValue(i,j));
            cell_tvs.get(n).setText(s);
            cell_tvs.get(n).setTextColor(Color.BLACK);
            cell_tvs.get(n).setBackgroundColor(Color.GRAY);
        }
    }

//     Start the timer
    private void startTimer() {
        final TextView timeView = (TextView) findViewById(R.id.timerTextView);
        final Handler handler = new Handler();
        running = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours =clock/3600;
                int minutes = (clock%3600) / 60;
                int seconds = clock%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, seconds);
                timeView.setText("Timer" + time);

                if (running) {
                    clock++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the timer callbacks to prevent memory leaks
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
}
