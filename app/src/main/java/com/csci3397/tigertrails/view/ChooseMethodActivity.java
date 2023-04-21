package com.csci3397.tigertrails.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.csci3397.tigertrails.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChooseMethodActivity extends AppCompatActivity {

    FloatingActionButton backButton;
    CardView drawCard;
    CardView walkCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_method);

        Context context = this;

        backButton = findViewById(R.id.backButton);

        drawCard = findViewById(R.id.drawCard);
        walkCard = findViewById(R.id.walkCard);

        //when draw card is clicked, take user to draw path screen
        drawCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DrawPathActivity.class);
                startActivity(intent);
            }
        });

        //when walk card is clicked, take user to walk path screen
        walkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WalkPathActivity.class);
                startActivity(intent);
            }
        });

        //TODO: change so that the back button takes user back to the last fragment they were on (LOW PRIORITY)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}