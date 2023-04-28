package com.csci3397.tigertrails.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.model.Path;
import com.csci3397.tigertrails.model.bRecyclerViewAdapter;
import com.csci3397.tigertrails.model.sRecyclerViewAdapter;
import com.csci3397.tigertrails.model.uRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private DatabaseReference pathsRef = FirebaseDatabase.getInstance().getReference("paths");

    private RecyclerView recyclerView;

    private boolean onBookmarked;

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

        onBookmarked = false;

        username = view.findViewById(R.id.username);
        //after getting a user base, this won't be hardcoded
        username.setText("Administrator");

        myPathsButton = view.findViewById(R.id.myPathsButton);
        bookmarkedButton = view.findViewById(R.id.bookmarkedButton);

        int white = ContextCompat.getColor(getContext(), R.color.white);
        int maroon = ContextCompat.getColor(getContext(), R.color.maroon);
        bookmarkedButton.setBackgroundColor(Color.TRANSPARENT);

        pathsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Path> allPaths = new ArrayList<Path>();
                for (DataSnapshot pathSnapshot : dataSnapshot.getChildren()) {
                    Path path = pathSnapshot.getValue(Path.class);
                    allPaths.add(path);
                }
                recyclerView = view.findViewById(R.id.userRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                if (!onBookmarked) {
                    uRecyclerViewAdapter adapter = new uRecyclerViewAdapter(getContext(), allPaths);
                    recyclerView.setAdapter(adapter);
                } else {
                    bRecyclerViewAdapter adapter = new bRecyclerViewAdapter(getContext(), allPaths);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });



        myPathsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onBookmarked) {
                    onBookmarked = false;
                    myPathsButton.setBackgroundColor(maroon);
                    myPathsButton.setTextColor(white);
                    bookmarkedButton.setBackgroundColor(Color.TRANSPARENT);
                    bookmarkedButton.setTextColor(maroon);

                    //may have to update recycler view here too
                    pathsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<Path> allPaths = new ArrayList<Path>();
                            for (DataSnapshot pathSnapshot : dataSnapshot.getChildren()) {
                                Path path = pathSnapshot.getValue(Path.class);
                                allPaths.add(path);
                            }
                            //recyclerView = view.findViewById(R.id.userRecyclerView);
                            //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            uRecyclerViewAdapter adapter = new uRecyclerViewAdapter(getContext(), allPaths);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        bookmarkedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!onBookmarked) {
                    onBookmarked = true;
                    bookmarkedButton.setBackgroundColor(maroon);
                    bookmarkedButton.setTextColor(white);
                    myPathsButton.setBackgroundColor(Color.TRANSPARENT);
                    myPathsButton.setTextColor(maroon);

                    //update recycler view
                    pathsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<Path> allPaths = new ArrayList<Path>();
                            for (DataSnapshot pathSnapshot : dataSnapshot.getChildren()) {
                                Path path = pathSnapshot.getValue(Path.class);
                                allPaths.add(path);
                            }
                            //recyclerView = view.findViewById(R.id.userRecyclerView);
                            //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            bRecyclerViewAdapter adapter = new bRecyclerViewAdapter(getContext(), allPaths);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

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