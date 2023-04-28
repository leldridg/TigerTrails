package com.csci3397.tigertrails.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.model.Path;
import com.csci3397.tigertrails.model.sRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

//TODO: make a way to hide bottom nav bar while search bar is focused? / keboard displayed?
public class SearchFragment extends Fragment {

    private DatabaseReference pathsRef = FirebaseDatabase.getInstance().getReference("paths");

    private ArrayList<Path> allPaths = new ArrayList<Path>();
    private ArrayList<Path> filteredPaths = new ArrayList<Path>();
    private SearchView searchView;

    private RecyclerView recyclerView;
    private sRecyclerViewAdapter adapter;

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

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        //set up paths
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allPaths = new ArrayList<Path>();
        filteredPaths = new ArrayList<Path>();
        adapter = new sRecyclerViewAdapter(getContext(), filteredPaths);
        recyclerView.setAdapter(adapter);

        pathsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allPaths.clear();
                for (DataSnapshot pathSnapshot : dataSnapshot.getChildren()) {
                    Path path = pathSnapshot.getValue(Path.class);
                    allPaths.add(path);
                }
                // Reverse the order of the allPaths list
                Collections.reverse(allPaths);
                filterList(searchView.getQuery().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void filterList(String text) {
        filteredPaths.clear();
        for(Path path : allPaths) {
            if(path.getPathName().toLowerCase().contains(text.toLowerCase())) {
                filteredPaths.add(path);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
