package com.csci3397.tigertrails.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.csci3397.tigertrails.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DrawPathActivity extends AppCompatActivity {

    FloatingActionButton backButton;
    FloatingActionButton exitButton;

    ImageButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_path);

        Context context = this;

        toggle = findViewById(R.id.toggleModeButton);
        toggle.setColorFilter(Color.GREEN);

        backButton = findViewById(R.id.backButton);
        exitButton = findViewById(R.id.exitButton);

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