package com.csci3397.tigertrails.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.model.Path;
import com.csci3397.tigertrails.model.sRecyclerViewAdapter;

import java.util.ArrayList;

//TODO: make a way to hide bottom nav bar while search bar is focused? / keboard displayed?
public class SearchFragment extends Fragment {

    //may want to change to different representation
    private ArrayList<Path> paths = new ArrayList<Path>();

    private RecyclerView recyclerView;

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpPaths();

        recyclerView = view.findViewById(R.id.searchRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sRecyclerViewAdapter adapter = new sRecyclerViewAdapter(getContext(), paths);
        recyclerView.setAdapter(adapter);
    }

    private void setUpPaths() {
        for(int i = 0; i < 16; i++) {
            paths.add(new Path("Test Path", .5, 3));
        }
    }
}