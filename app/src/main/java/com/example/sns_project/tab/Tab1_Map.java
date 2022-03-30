package com.example.sns_project.tab;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sns_project.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


public class Tab1_Map extends Fragment implements OnMapReadyCallback{

    GoogleMap gMap;
    MapView mapView = null;
    MarkerOptions myLocationPin;

    //Places API - PlacesListener
    List<Marker> previous_marker = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tab1__map, container, false);

        /*Fragment내에서는 mapView로 지도를 실행*/
        mapView = (MapView)rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this); // 비동기적 방식으로 구글 맵 실행

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        previous_marker = new ArrayList<Marker>();


        initLocationManager();


    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;


        LatLng SEOUL = new LatLng(37.56, 126.97);

        //구글맵 실행시 시작 위치로 카메라 이동과 줌 사이즈
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        gMap.addMarker(markerOptions);



    }

    public void initLocationManager() {
        LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Log.d("GPS",  "startLocationService  Latitude : "+ latitude + "+ Longitude:"+ longitude);
            }

            GPSListener gpsListener = new GPSListener();
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance, gpsListener);


        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            Log.d("GPS",  "onLocationChanged  Latitude : "+ latitude + "+ Longitude:"+ longitude);

            LatLng curPoint = new LatLng(latitude, longitude);
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

            if (myLocationPin == null) {
                myLocationPin = new MarkerOptions();
                myLocationPin.position(curPoint);
                myLocationPin.title("내 위치");
                myLocationPin.snippet("hi hi");
                myLocationPin.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin));
                gMap.addMarker(myLocationPin);

//                initPlaceLocation(curPoint);

            } else {
                myLocationPin.position(curPoint);
            }
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }


    }




    public void initPlaceLocation(LatLng location){

        PlacesAPIListener placesAPIListener = new PlacesAPIListener();

        gMap.clear();//지도 클리어

        if (previous_marker != null){
            previous_marker.clear();//지역정보 마커 클리어
        }

    }


    private class PlacesAPIListener {
    }
}