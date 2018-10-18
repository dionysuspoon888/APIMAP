package com.example.apimap;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailUserActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button b_detailuser_back;
    private ImageView iv_detailuser_image;
    private TextView tv_detailuser_name;
    private TextView tv_detailuser_email;
    SupportMapFragment mapFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iv_detailuser_image = findViewById(R.id.iv_detailuser_image);
        tv_detailuser_name = findViewById(R.id.tv_detailuser_name);
        tv_detailuser_email = findViewById(R.id.tv_detailuser_email);

        Glide.with(this).load(GlobalConstants.UserData.getPicture()).into(iv_detailuser_image);
        tv_detailuser_name.setText(GlobalConstants.UserData.getName());
        tv_detailuser_email.setText(GlobalConstants.UserData.getEmail());


        b_detailuser_back = findViewById(R.id.b_detailuser_back);
        b_detailuser_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
            }
        });

        if(!isNetworkConnected()){
         Toast.makeText(this, "No Internet Communication", Toast.LENGTH_LONG).show();
        }
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
            mMap.setMinZoomPreference(4.0f);
       //    mMap.setMaxZoomPreference(20.0f);



        LatLng location = new LatLng(GlobalConstants.UserData.getLatitude(), GlobalConstants.UserData.getLongitude());

        mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}