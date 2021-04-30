package com.codingproject.nationalparktool.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.codingproject.nationalparktool.R;
import com.codingproject.nationalparktool.api.NationalParkServiceApi;
import com.codingproject.nationalparktool.constants.ApiKey;
import com.codingproject.nationalparktool.servicesdata.ServicesData;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicesActivity extends AppCompatActivity {

    // declare class variables
    private String parkCode;
    private String parkName;
    private Toolbar toolbar;
    private ImageView noDataImageView;

    // declare gui variables
    ListView servicesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        // get ui elements
        servicesListView = findViewById(R.id.servicesListView);
        noDataImageView = findViewById(R.id.noDataImageView);

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
        Call<ServicesData> call = nationalParkServiceApi.getServices(parkCode, ApiKey.API_KEY);

        // retrofit will create sub-thread to handle API requests
        call.enqueue(new Callback<ServicesData>() {
            @Override
            public void onResponse(Call<ServicesData> call, Response<ServicesData> response) {
                if (!response.isSuccessful()) { // display error codes
                    Log.i("ServicesActivity.java", "ERROR CODE: " + response.code());
                    return;
                }
                // build services data object array
                ServicesData services = response.body();
                // check for no data available
                if (services.getData().size() == 0 || services.getData() == null) { // no data
                    // no services available
                    noDataImageView.setImageResource(R.drawable.no_result_icon);
                    noDataImageView.setVisibility(View.VISIBLE);
                    servicesListView.setVisibility(View.GONE);
                }
                else { // data available
                    // copy data
                    int length = services.getData().size(); // get length
                    String[] servicesArray = new String[length];
                    for (int i = 0; i < length; i++) {
                        servicesArray[i] = services.getData().get(i).get(0).getName();
                    }
                    // display activities to gui
                    ArrayAdapter activityAdapter = new ArrayAdapter<String>(ServicesActivity.this, R.layout.activity_services_listview_item, servicesArray);
                    servicesListView.setAdapter(activityAdapter);
                }
            }
            @Override
            public void onFailure(Call<ServicesData> call, Throwable t) { // display failure message
                Log.i("ServicesActivity.java", "FAILURE MESSAGE: " + t.getMessage());
            }
        });
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