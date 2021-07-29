package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.elements.Record;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap gMap;
    private double dLatitude = 0.0;
    private double dLongtitude = 0.0;
    final int ZOOM = 15;

    public MapFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.map_fragment,container,false);
       SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
       return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng latLng = new LatLng(dLatitude, dLongtitude);
        gMap.addMarker(new MarkerOptions().position(latLng).title(""+latLng));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,ZOOM));
    }


    public void getUserLocation(Record record) {
        dLatitude = record.getLat();
        dLongtitude = record.getLng();
        onMapReady(gMap);
    }
}
