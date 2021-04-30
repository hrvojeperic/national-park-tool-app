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
import android.widget.ImageView;
import android.widget.ListView;

import com.codingproject.nationalparktool.R;
import com.codingproject.nationalparktool.adapterdata.TourGuideItem;
import com.codingproject.nationalparktool.adapters.TourGuideAdapter;
import com.codingproject.nationalparktool.api.NationalParkServiceApi;
import com.codingproject.nationalparktool.constants.ApiKey;
import com.codingproject.nationalparktool.tourguidesdata.TourGuidesData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourGuidesActivity extends AppCompatActivity {

    // declare class variables
    private String parkCode;
    private String parkName;
    private Toolbar toolbar;

    // declare gui variables
    ListView tourGuidesListView;
    ImageView noDataImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guides);

        // get ui elements
        tourGuidesListView = findViewById(R.id.tourGuidesListView);
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
        Call<TourGuidesData> call = nationalParkServiceApi.getTourGuides(parkCode, ApiKey.API_KEY);

        // retrofit will create sub-thread to handle API requests
        call.enqueue(new Callback<TourGuidesData>() {
            @Override
            public void onResponse(Call<TourGuidesData> call, Response<TourGuidesData> response) {
                if (!response.isSuccessful()) { // display error codes
                    Log.i("TourGuidesActivity.java", "ERROR CODE: " + response.code());
                    return;
                }
                // build tour guides object array
                TourGuidesData tourGuides = response.body();

                // check if data exists
                if (tourGuides.getData() == null || tourGuides.getData().size() == 0) { // no data
                    // no data
                    noDataImageView.setImageResource(R.drawable.no_result_icon);
                    noDataImageView.setVisibility(View.VISIBLE);
                    tourGuidesListView.setVisibility(View.GONE);
                }
                else { // data available
                    // copy data
                    final ArrayList<TourGuideItem> arrayList = new ArrayList<TourGuideItem>();
                    int length = tourGuides.getData().size(); // get length
                    for (int i = 0; i < length; i++) {
                        String name = tourGuides.getData().get(i).getTitle();
                        String description = tourGuides.getData().get(i).getDescription();
                        String duration = tourGuides.getData().get(i).getDurationMin() + "-"
                                            + tourGuides.getData().get(i).getDurationMax()
                                            + tourGuides.getData().get(i).getDurationUnit();
                        String stops = "" + tourGuides.getData().get(i).getStops().size();

                        arrayList.add(new TourGuideItem(name, description, duration, stops));
                    }
                    // display data
                    TourGuideAdapter numbersArrayAdapter = new TourGuideAdapter(TourGuidesActivity.this, arrayList);
                    ListView numbersListView = findViewById(R.id.tourGuidesListView);
                    numbersListView.setAdapter(numbersArrayAdapter);

                }

            }
            @Override
            public void onFailure(Call<TourGuidesData> call, Throwable t) { // display failure message
                Log.i("TourGuidesActivity.java", "FAILURE MESSAGE: " + t.getMessage());
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