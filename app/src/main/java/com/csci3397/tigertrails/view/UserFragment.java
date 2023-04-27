package com.csci3397.tigertrails.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csci3397.tigertrails.R;

import org.w3c.dom.Text;

public class UserFragment extends Fragment {
    private Button myPathsButton;
    private Button bookmarkedButton;

    private TextView username;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.username);
        //after getting a user base, this won't be hardcoded
        username.setText("Administrator");

        myPathsButton = view.findViewById(R.id.myPathsButton);
        bookmarkedButton = view.findViewById(R.id.bookmarkedButton);

//        myPathsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myPathsButton.setBackgroundColor(Color.parseColor("#722130"));
//                myPathsButton.setTextColor(Color.WHITE);
//            }
//        });
//
//        bookmarkedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bookmarkedButton.setBackgroundColor(Color.parseColor("#722130"));
//                bookmarkedButton.setTextColor(Color.WHITE);
//            }
//        });
    }
}