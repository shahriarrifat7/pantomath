package com.example.sr7.panto_math;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivateKey;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker=null;
    private Marker Tmarker=null;
    private double diff=0.00001;
    private Location mLocation;
    private GPSHelper gpsHelper;
    private FirebaseAuth mAuth;
    private double longitude,latitude,lat,lan;
    private Button done;
    private String tuition_id,teacher_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        done=(Button)findViewById(R.id.done);
        mAuth=FirebaseAuth.getInstance();

        Intent recievedIntent= getIntent();
        tuition_id = recievedIntent.getStringExtra("tid");
        teacher_id = recievedIntent.getStringExtra("teacherId");
        lat = recievedIntent.getDoubleExtra("lat",0.0);
        lan = recievedIntent.getDoubleExtra("lan",0.0);

        if(lan>0 || lat>0) {
           done.setVisibility(View.GONE);
        }
        toastMessage(String.valueOf(lat)+" "+String.valueOf(lan));
        //Tmarker.setPosition(new LatLng(lat, lan));


        gpsHelper= new GPSHelper(getApplicationContext());
        mLocation = gpsHelper.getLocation();
        try {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
        }catch (Exception e){
            //toastMessage(String.valueOf(R.string.reqLocShare));
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toastMessage(tuition_id+" "+teacher_id);
                final DatabaseReference  myRef = FirebaseDatabase.getInstance().getReference("tuitions").child(tuition_id);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Tution tution= dataSnapshot.getValue(Tution.class);
                        tution.setStatus("INVITED");
                        myRef.setValue(tution);
                        if(marker!=null){
                            tution.setLan(marker.getPosition().longitude);
                            tution.setLat(marker.getPosition().latitude);
                        }

                        DatabaseReference myRef1=FirebaseDatabase.getInstance().getReference("teacher_invite").child(teacher_id).child(tuition_id);
                        myRef1.setValue(tution);
                        Intent intent=new Intent(getApplicationContext(),StudentHomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        final LatLng latLng = new LatLng(latitude, longitude);
        mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(75)
                .strokeWidth(2f));
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(21);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        Marker mark= mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("I'm here...")
                .snippet("Its My Location")
                .rotation((float) -15.0)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        Tmarker=mMap.addMarker(new MarkerOptions()
                .snippet(tuition_id)
                .rotation((float)12.0)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .position(new LatLng(lat,lan)));



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(marker!=null) {
                    marker.remove();
                }
                marker=mMap.addMarker(new MarkerOptions()
                        .snippet("My Home")
                        .rotation((float)0.0)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .position(latLng));
            }
        });

//.fillColor(0x55ffff99));
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
