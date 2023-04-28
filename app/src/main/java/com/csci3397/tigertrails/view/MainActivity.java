package com.csci3397.tigertrails.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.model.Path;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ConnectivityManager.NetworkCallback networkCallback;
    BottomNavigationView bottomNavigationView;

    SearchFragment searchFragment = new SearchFragment();
    UserFragment userFragment = new UserFragment();

    FloatingActionButton makePathButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;


        //the current code on android's docs is deprecated
        //not really sure how to see if there is connection when you have no wifi on app open
        /*public boolean isOnline() {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }*/

        //check network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Network currentNetwork = connectivityManager.getActiveNetwork();

        //if null, no internet
        if(currentNetwork == null) {
            //show warning dialog
            //open new dialog box to inform user of error
            Dialog warningDialog = new Dialog(context);
            warningDialog.setContentView(R.layout.warning_dialog_layout);
            warningDialog.show();

            Button close = warningDialog.findViewById(R.id.acknowledge);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    warningDialog.dismiss();
                }
            });
        }

        //System.out.println(currentNetwork);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                //network is available
            }

            @Override
            public void onLost(Network network) {
                //network is lost
                //show warning dialog
                //open new dialog box to inform user of error
                Dialog warningDialog = new Dialog(context);
                warningDialog.setContentView(R.layout.warning_dialog_layout);
                warningDialog.show();

                Button close = warningDialog.findViewById(R.id.acknowledge);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        warningDialog.dismiss();
                    }
                });
            }
        };

        connectivityManager.registerDefaultNetworkCallback(networkCallback);


        makePathButton = findViewById(R.id.makePathButton);

        //when fab is clicked, take user to choose method screen
        makePathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //unregister network callback to prevent memory leaks
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

}