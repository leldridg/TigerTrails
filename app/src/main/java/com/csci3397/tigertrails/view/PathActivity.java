package com.csci3397.tigertrails.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.model.Path;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PathActivity extends AppCompatActivity {

    FloatingActionButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_screen);

        Context context = this;

        backButton = findViewById(R.id.pathBackButton);

        // Get the selected path from the intent extras
//        Path clickedPath = getIntent().getParcelableExtra("clicked_path");
        Path path = (Path) getIntent().getSerializableExtra("clicked_path");

        // Use the selected path to populate the UI
        TextView pathNameTextView = findViewById(R.id.path);
        pathNameTextView.setText(path.getPathName());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
