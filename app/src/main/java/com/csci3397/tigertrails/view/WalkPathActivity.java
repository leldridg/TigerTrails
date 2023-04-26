package com.csci3397.tigertrails.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.csci3397.tigertrails.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WalkPathActivity extends AppCompatActivity {

    FloatingActionButton backButton;
    FloatingActionButton exitButton;

    ImageButton toggleLocButton;
    ImageButton addStopButton;
    ImageButton finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_path);

        Context context = this;

        backButton = findViewById(R.id.backBtn);
        exitButton = findViewById(R.id.exitBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChooseMethodActivity.class);
                startActivity(intent);
            }
        });

        //TODO: change so that the exit button takes user back to the last fragment they were on (LOW PRIORITY)
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }


}