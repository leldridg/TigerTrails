package com.csci3397.tigertrails.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.model.Path;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//this is a different test
    BottomNavigationView bottomNavigationView;

    SearchFragment searchFragment = new SearchFragment();
    UserFragment userFragment = new UserFragment();

    FloatingActionButton makePathButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;

        makePathButton = findViewById(R.id.makePathButton);
        makePathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when fab is clicked, take user to choose method screen
                Intent intent = new Intent(context, ChooseMethodActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        //Code below is a tentative solution for bottom navigation
        //Due to the way that I made the bottom nav bar, I couldn't seem to get the bottom nav to work through the same method we did in class
        //I think this can be fixed, but I think we would have to create an additional fragment and I couldn't be bothered

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, searchFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.miSearch:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, searchFragment).commit();
                        return true;
                    case R.id.miUser:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, userFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}