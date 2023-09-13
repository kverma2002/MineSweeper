package com.example.gridlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameoverscreen);

        Intent intent = getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        String num = intent.getStringExtra(MainActivity.EXTRA_NUMBER);

        TextView tv1 = (TextView) findViewById(R.id.time);
        TextView tv2 = (TextView) findViewById(R.id.job);

        tv1.setText("You finished in " + num + " seconds");
        tv2.setText(text);

        Button but = (Button) findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operGame();
            }
        });

    }

    private void operGame() {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
