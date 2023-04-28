package com.csci3397.tigertrails.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.csci3397.tigertrails.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WalkPathActivity extends AppCompatActivity {

    ConnectivityManager.NetworkCallback networkCallback;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //unregister network callback to prevent memory leaks
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }


}