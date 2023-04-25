package com.csci3397.tigertrails.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.csci3397.tigertrails.R;
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

import java.util.ArrayList;

public class MapsFragment extends Fragment {

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
            if (parentActivity instanceof DrawPathActivity) {
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
                        //add point to map
                        Marker m = googleMap.addMarker(new MarkerOptions().position(latLng));
                        markers.add(m);
                        //add point to logical list of points
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
                });

                //undo button functionality
                undoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //checking both like this may be redundant or lead to errors
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

            } else if (parentActivity instanceof WalkPathActivity) {

            } else if (parentActivity instanceof PathActivity) {

            } else {
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
}