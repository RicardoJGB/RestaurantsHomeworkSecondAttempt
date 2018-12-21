package com.mobileapps.restaurantshomeworksecondattempt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private GoogleMap mMap;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 157;

    private LocationRequest mLocationRequest;
    public static final long UPDATE_INTERVAL=10000;
    public static final long FASTEST_INTERVAL=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng minasGrill = new LatLng(33.9230688, -84.4797826);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.minas_grill))
                .position(minasGrill)
                .title("2555 Delk Rd, Marietta, GA 30067"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(minasGrill));
        LatLng hardees = new LatLng(33.9222713, -84.4795714);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.hardees3))
                .position(hardees)
                .title("2520 Delk Rd SE, Marietta, GA 30067"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hardees));
        LatLng mcDonalds = new LatLng(33.9214132, -84.4701858);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.mcdonalds))
                .position(mcDonalds)
                .title("1305 Powers Ferry Rd, Marietta, GA 30067"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mcDonalds));
        LatLng firehouse = new LatLng(33.9229967, -84.469525);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.firehouse_small))
                .position(firehouse)
                .title("2900 Delk Rd #550, Marietta, GA 30067"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(firehouse));
        LatLng hardRock = new LatLng(33.7604159, -84.3886548);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                .fromResource(R.drawable.hardrock))
                .position(hardRock)
                .title("215 Peachtree St NE, Atlanta, GA 30303"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hardRock));

        final Geocoder geocoder = new Geocoder(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng minasGrill = new LatLng(33.9222489, -84.4773122);
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.minas_grill))
                        .position(minasGrill));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(minasGrill));
                List<Address> addresses = null; try {
                    addresses = geocoder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String title = address +"-"+city+"-"+state;
                marker.setTitle(title);
                return true;
            }
        });
    }

    private void runtimePermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d("PERMISSION_REQUESTED","runtimePermissions: Runtime Permission being requested");
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getLastKnownLocation();
                        startLocationUpdates();
                    }
                } else {

                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if(location!=null){
            LatLng lastLocationLatLng=new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(lastLocationLatLng).title("You are here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLocationLatLng));
            mMap.setMinZoomPreference(10);
            Geocoder geocoder = new Geocoder(this);
            try{
                List<Address> geocodeFromLocation = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(),1);
                String address="Alexandria Desert Rd Giza Governorate, Egypt";
                Log.d("TAG","onSuccess: "+geocodeFromLocation.get(0).getAddressLine(0));
                List<Address> reverseGeoCode= geocoder.getFromLocationName(address,1);
                String latLng = reverseGeoCode.get(0).getLatitude() + " : "+reverseGeoCode.get(0).getLongitude();
                Log.d("TAG","onLocationChanged: "+latLng);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void startLocationUpdates(){
        mLocationRequest=new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        //Create LocationSettingRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        //Check wether location settings are satisfied
        //
        SettingsClient settingsClient=LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //new Google API SDK v11 uses getFusedLocationProviderClient(this)
            getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest,new LocationCallback(){
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            onLocationChanged(locationResult.getLastLocation());
                        }
                    },
                    Looper.myLooper());

        }

    }

    public void getLastKnownLocation(){
        //Get last known recent location using new Google Play SErvices SDK
        FusedLocationProviderClient locationClient=getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            locationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                Log.d("TAG","onSuccess: "+location.toString());
                                // LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MapDemoActivity","Error trying to get last GPS location");
                            e.printStackTrace();
                        }
                    });
        }

    }
}
