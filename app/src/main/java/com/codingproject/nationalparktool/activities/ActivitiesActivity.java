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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.codingproject.nationalparktool.R;
import com.codingproject.nationalparktool.activitiesdata.Activities;
import com.codingproject.nationalparktool.api.NationalParkServiceApi;
import com.codingproject.nationalparktool.constants.ApiKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivitiesActivity extends AppCompatActivity {

    // declare class variables
    private String parkCode;
    private String parkName;
    private Toolbar toolbar;

    // declare gui variables
    ListView activitiesListView;
    ImageView noDataImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        // get ui elements
        activitiesListView = findViewById(R.id.activitiesListView);
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
        Call<Activities> call = nationalParkServiceApi.getActivities(parkCode, ApiKey.API_KEY);

        // retrofit will create sub-thread to handle API requests
        call.enqueue(new Callback<Activities>() {
            @Override
            public void onResponse(Call<Activities> call, Response<Activities> response) {
                if (!response.isSuccessful()) { // display error codes
                    Log.i("ActivitiesActivity.java", "ERROR CODE: " + response.code());
                    return;
                }
                // build activities object array
                Activities activities = response.body();
                if (activities.getData().get(0).getActivities().size() == 0 || activities.getData().get(0).getActivities() == null) { // no data
                    // no services available
                    noDataImageView.setImageResource(R.drawable.no_result_icon);
                    noDataImageView.setVisibility(View.VISIBLE);
                    activitiesListView.setVisibility(View.GONE);
                }
                else {
                    // copy data
                    int length = activities.getData().get(0).getActivities().size();
                    String[] activityArray = new String[length];
                    for (int i = 0; i < length; i++) {
                        activityArray[i] = activities.getData().get(0).getActivities().get(i).getName();
                    }
                    // display activities to gui
                    ArrayAdapter activityAdapter = new ArrayAdapter<String>(ActivitiesActivity.this, R.layout.activity_activities_listview_item, activityArray);
                    activitiesListView.setAdapter(activityAdapter);
                }
            }
            @Override
            public void onFailure(Call<Activities> call, Throwable t) { // display failure message
                Log.i("ActivitiesActivity.java", "FAILURE MESSAGE: " + t.getMessage());
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