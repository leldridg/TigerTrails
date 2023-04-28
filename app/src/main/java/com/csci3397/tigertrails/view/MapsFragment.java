package com.csci3397.tigertrails.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csci3397.tigertrails.R;
import com.csci3397.tigertrails.model.Path;
import com.csci3397.tigertrails.model.Point;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    //private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference pathsRef = FirebaseDatabase.getInstance().getReference("paths");

    private long numPaths = 0;

    //var used in onMapReady when in draw path parent activity
    private boolean stopToggleOn = false;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {



        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            pathsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    numPaths = dataSnapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                }
            });

            //ensure that when map is displayed, stopToggleOn is false
            stopToggleOn = false;

            //overlay updated trinity map onto google maps
            LatLngBounds trinityBounds = new LatLngBounds(new LatLng(29.456563485593676, -98.48813698915492),
                    new LatLng(29.466813470420874, -98.47901476675511));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trinityBounds.getCenter(), 16.3f));
            GroundOverlayOptions trinityMap = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.cropped_tiger_trails_map))
                    .positionFromBounds(trinityBounds);
            googleMap.addGroundOverlay(trinityMap);

            //determine which activity the fragment is in; based on the activity, set up how to react
            Activity parentActivity = getActivity();
            //System.out.println(parentActivity);
            if (parentActivity instanceof PathActivity) {
                //System.out.println("In PathActivity");
                // Get the selected path from the intent extras
                //Path clickedPath = getIntent().getParcelableExtra("clicked_path");
                Path path = (Path) getActivity().getIntent().getSerializableExtra("clicked_path");

                // Use the selected path to populate the UI
                TextView pathName = getActivity().findViewById(R.id.path);
                TextView distance = getActivity().findViewById(R.id.pDistance);
                TextView estTime = getActivity().findViewById(R.id.pEstTime);
                TextView desc = getActivity().findViewById(R.id.description);

                //text elements
                pathName.setText(path.getPathName());
                distance.setText(String.format("%.2f km", path.getDistance()));
                estTime.setText(String.format("%.2f min", path.getMinutes()));
                desc.setText(path.getDescription());

                //map
                ArrayList<Point> points = path.getPoints();
                //System.out.println("Size of points: " + points.size());
                //display polylines between points, add markers for stops
                for (int i = 0; i < points.size() - 1; i++) {
                    Point curr = points.get(i);
                    Point next = points.get(i + 1);
                    //System.out.println(curr.getLatLng());
                    googleMap.addPolyline(new PolylineOptions()
                            .add(curr.getLatLng(), next.getLatLng())
                            .color(Color.RED)
                    );
                    if (curr.isStop()) {
                        googleMap.addMarker(new MarkerOptions().position(curr.getLatLng()).title(curr.getDesc()));
                    }
                }
                //System.out.println(points.get(points.size()-1).isStop());
                //System.out.println(points.get(points.size()-1).getDesc());
                if (points.get(points.size() - 1).isStop()) {
                    googleMap.addMarker(new MarkerOptions().position(points.get(points.size() - 1).getLatLng()).title(points.get(points.size() - 1).getDesc()));
                }
            } else if (parentActivity instanceof DrawPathActivity) {
                //System.out.println("In DrawPathActivity");
                //access DrawPathActivity's ImageButtons
                ImageButton undoButton = (ImageButton) getActivity().findViewById(R.id.undoButton);
                ImageButton toggleButton = (ImageButton) getActivity().findViewById(R.id.toggleModeButton);
                ImageButton finishButton = (ImageButton) getActivity().findViewById(R.id.finishButton);

                //arraylist of points and arraylist of markers correspond to one another
                ArrayList<Point> points = new ArrayList<Point>();
                ArrayList<Marker> markers = new ArrayList<Marker>();

                ArrayList<Polyline> lines = new ArrayList<Polyline>();

                //TODO: add ability to differentiate between normal point, stop

                //listens for user clicks
                //when the user clicks, a marker is created based on the LatLng clicked
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        //if regular pin drop
                        if (!stopToggleOn) {
                            //add green point to map
                            Marker m = googleMap.addMarker(new MarkerOptions().position(latLng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            );
                            markers.add(m);
                            //add regular point to logical list of points
                            Point p = new Point(latLng);
                            points.add(p);
                            if(points.size() > 1) {
                                Polyline l = googleMap.addPolyline(new PolylineOptions()
                                        .add(points.get(points.size()-1).getLatLng(), points.get(points.size()-2).getLatLng())
                                        .color(Color.RED)
                                );
                                lines.add(l);
                            }
                        }
                        //if stop
                        if (stopToggleOn) {
                            //open new dialog box to prompt user for stop description
                            Dialog stopDialog = new Dialog(getContext());
                            stopDialog.setContentView(R.layout.add_stop_dialog_layout);
                            stopDialog.show();

                            ImageButton close = stopDialog.findViewById(R.id.closeButton);
                            Button add = stopDialog.findViewById(R.id.addStop);
                            TextView error = stopDialog.findViewById(R.id.sErrorPrompt);
                            EditText desc = stopDialog.findViewById(R.id.stopDescInput);

                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    stopDialog.dismiss();
                                }
                            });

                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String inDesc = desc.getText().toString().trim();
                                    if (inDesc.equals("")) {
                                        error.setText("Please enter a stop description.");
                                    } else {
                                        Marker m = googleMap.addMarker(new MarkerOptions().position(latLng).title(inDesc)
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                        );
                                        markers.add(m);
                                        //add regular point to logical list of points
                                        Point p = new Point(latLng, inDesc);
                                        points.add(p);
                                        if(points.size() > 1) {
                                            Polyline l = googleMap.addPolyline(new PolylineOptions()
                                                    .add(points.get(points.size()-1).getLatLng(), points.get(points.size()-2).getLatLng())
                                                    .color(Color.RED)
                                            );
                                            lines.add(l);
                                        }
                                        stopDialog.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });

                //undo button functionality
                undoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //checking both like this may be redundant
                        int lastP = points.size()-1;
                        if(lastP > 0) { //if there is a polyline to remove
                            int lastL = lines.size()-1;
                            lines.get(lastL).remove();
                            lines.remove(lastL);
                        }
                        if (lastP >= 0) { //if there is a point to remove
                            points.remove(lastP);
                        }
                        int lastM = markers.size()-1;
                        if (lastM >= 0) { //if there is a marker to remove
                            markers.get(lastM).remove();
                            markers.remove(lastM);
                        }
                    }
                });

                toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO:implement toggle feature
                        if (!stopToggleOn) {
                            toggleButton.setImageResource(R.drawable.ic_add_pin);
                        }
                        if (stopToggleOn) {
                            toggleButton.setImageResource(R.drawable.ic_pin);
                        }
                        stopToggleOn = !stopToggleOn;
                    }
                });

                finishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //set up prompt for ask user for path name and description and to confirm they are done
                        Dialog finishDialog = new Dialog(view.getContext());
                        finishDialog.setContentView(R.layout.finish_path_dialog_layout);
                        finishDialog.show();

                        ImageButton close = finishDialog.findViewById(R.id.closeButton);
                        Button create = finishDialog.findViewById(R.id.createPathButton);
                        TextView error = finishDialog.findViewById(R.id.errorPrompt);
                        EditText inputPathName = finishDialog.findViewById(R.id.pathNameInput);
                        EditText inputDescription = finishDialog.findViewById(R.id.descriptionInput);

                        //allows user to cancel finish
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finishDialog.dismiss();
                            }
                        });

                        //checks to make sure path can be created (has name, desc, at least 2 points)
                        //if cannot be created, display error message
                        //if can be created, create a new path and add to the ArrayList of the current user's paths (and maybe all paths?)
                        create.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String inName = inputPathName.getText().toString().trim();
                                String inDesc = inputDescription.getText().toString().trim();
                                if (points.size() < 2) {
                                    error.setText("Please add at least two points to your path.");
                                } else if(inName.equals("") || inDesc.equals("")) {
                                    error.setText("Please enter both a path name and description.");
                                } else {
                                    error.setText("");
                                    //calculate total path distance in kilometers
                                    double km = 0;
                                    for(int i = 0; i < points.size()-1; i++) {
                                        LatLng point1 = points.get(i).getLatLng();
                                        LatLng point2 = points.get(i+1).getLatLng();
                                        km += distanceBetween(point1,point2);
                                    }
                                    // calculate estimated time to walk path in minutes
                                    double min = km * 11;
                                    // make new path
                                    Path newPath = new Path(numPaths + 1, "admin", inName, inDesc, km, min, points);
                                    // add to database
                                    pathsRef.child("" + (numPaths + 1)).setValue(newPath);
                                    //close dialog
                                    finishDialog.dismiss();
                                    //TODO: make intent go to user screen if possible
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });

            } else if (parentActivity instanceof WalkPathActivity) {

            }  else {
                //parentActivity is null
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    // helper method to calculate the distance between two LatLng points
    // constructed with help from StackOverflow / ChatGPT
    private double distanceBetween(LatLng p1, LatLng p2) {
        double R = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(p2.latitude - p1.latitude);
        double dLon = Math.toRadians(p2.longitude - p1.longitude);
        double lat1 = Math.toRadians(p1.latitude);
        double lat2 = Math.toRadians(p2.latitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c; // return distance in kilometers
    }

}