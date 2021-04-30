package com.codingproject.nationalparktool.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codingproject.nationalparktool.api.NationalParkServiceApi;
import com.codingproject.nationalparktool.constants.ApiKey;
import com.codingproject.nationalparktool.longlatdata.LongLatData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.codingproject.nationalparktool.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String parkCode;
    private String parkName;
    private double longitude;
    private double latitude;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // get intent data stored and extras
        Intent intent = getIntent();
        parkCode = intent.getStringExtra(MenuActivity.EXTRA_RES_ID_1);
        parkName = intent.getStringExtra(MenuActivity.EXTRA_RES_ID_2);

        // set action bar title
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(parkName);
        setSupportActionBar(toolbar);

        // set up the api request to be called
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NationalParkServiceApi nationalParkServiceApi = retrofit.create(NationalParkServiceApi.class);
        Call<LongLatData> call = nationalParkServiceApi.getLongLat(parkCode, ApiKey.API_KEY);

        // retrofit will create sub-thread to handle API requests
        call.enqueue(new Callback<LongLatData>() {
            @Override
            public void onResponse(Call<LongLatData> call, Response<LongLatData> response) {
                if (!response.isSuccessful()) { // display error codes
                    Log.i("MapActivity.java", "ERROR CODE: " + response.code());
                    return;
                }
                // build location data object array
                LongLatData location = response.body();
                // store location data
                longitude = Double.parseDouble(location.getData().get(0).getLongitude());
                latitude = Double.parseDouble(location.getData().get(0).getLatitude());
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapActivity.this);
            }
            @Override
            public void onFailure(Call<LongLatData> call, Throwable t) { // display failure message
                Log.i("MapActivity.java", "FAILURE MESSAGE: " + t.getMessage());
            }
        });

    }

    // callback is triggered when the map is ready to be used.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 15.0f; // up to 21 to control zoom
        // Add a marker in place and move the camera
        LatLng placeLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(placeLocation).title(parkName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLocation, zoomLevel));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // set up menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.parkListBookMarkItem:
                Intent bookmarkIntent = new Intent(this, BookmarkedActivity.class);
                bookmarkIntent.putExtra(NationalParkListActivity.EXTRA_RES_ID_4, NationalParkListActivity.nationalParkItemList);
                startActivity(bookmarkIntent);
                break;
            case R.id.faqItem:
                Intent faqIntent = new Intent(this, FaqActivity.class);
                startActivity(faqIntent);
                break;
            case R.id.contactUsItem:
                // start a dial intent with number
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + "8471911221"));
                startActivity(callIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}