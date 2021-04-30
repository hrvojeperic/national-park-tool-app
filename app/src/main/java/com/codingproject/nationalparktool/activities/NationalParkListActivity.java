package com.codingproject.nationalparktool.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.codingproject.nationalparktool.adapterdata.NationalParkItem;
import com.codingproject.nationalparktool.api.NationalParkServiceApi;
import com.codingproject.nationalparktool.constants.ApiKey;
import com.codingproject.nationalparktool.constants.ApiParkSize;
import com.codingproject.nationalparktool.parklistdata.ParkArray;
import com.codingproject.nationalparktool.adapters.ParkImageAdapter;
import com.codingproject.nationalparktool.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NationalParkListActivity extends AppCompatActivity {

    // declare data variables
    private String[] parkNameArray;
    private String[] parkCodeArray;
    private String[] parkImageArray;

    // declare variables for national park recycler view
    public static ArrayList<NationalParkItem> nationalParkItemList;
    private RecyclerView mRecycleView;
    private ParkImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;

    // keys for extras in intent
    protected static final String EXTRA_RES_ID_1 = "PARK_NAME";
    protected static final String EXTRA_RES_ID_2 = "PARK_CODE";
    protected static final String EXTRA_RES_ID_3 = "PARK_IMAGE";
    protected static final String EXTRA_RES_ID_4 = "PARK_OBJECT_ARRAY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_national_park_list);

        // set action bar title
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("National Parks");
        setSupportActionBar(toolbar);

        // set up the api request to be called
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NationalParkServiceApi nationalParkServiceApi = retrofit.create(NationalParkServiceApi.class);
        Call<ParkArray> call = nationalParkServiceApi.getParks(ApiParkSize.PARK_SIZE, ApiKey.API_KEY);

        // set up recycler view
        nationalParkItemList = new ArrayList<>();
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(NationalParkListActivity.this);
        mAdapter = new ParkImageAdapter(nationalParkItemList);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        // retrofit will create sub-thread to handle API requests
        call.enqueue(new Callback<ParkArray>() {
            @Override
            public void onResponse(Call<ParkArray> call, Response<ParkArray> response) {
                if (!response.isSuccessful()) { // display error codes
                    Log.i("NationalParkList.java", "ERROR CODE: " + response.code());
                    return;
                }
                // build park object array
                ParkArray parkList = response.body();
                int length = parkList.getData().size();
                // initialize array to hold api data
                parkNameArray = new String[length];
                parkCodeArray = new String[length];
                parkImageArray  = new String[length];
                for (int i = 0; i < length; i++) { // iterate through each park
                    parkNameArray[i] = parkList.getData().get(i).getFullName();
                    parkCodeArray[i] = parkList.getData().get(i).getParkCode();
                    if (!parkList.getData().get(i).getImages().isEmpty()) { // image found for park
                        parkImageArray[i] = parkList.getData().get(i).getImages().get(0).getUrl();
                    }
                    else { // no image found for park
                        parkImageArray[i] = "IMAGE NOT FOUND";
                    }
                }

                // build array of grid items
                nationalParkItemList = new ArrayList<>();
                for (int i = 0; i < parkNameArray.length; i++) {
                    nationalParkItemList.add(new NationalParkItem(parkCodeArray[i], parkImageArray[i], parkNameArray[i], R.drawable.bookmark_unfilled));
                }

                // set up recycler view
                mRecycleView = findViewById(R.id.recyclerView);
                mRecycleView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(NationalParkListActivity.this);
                mAdapter = new ParkImageAdapter(nationalParkItemList);
                mRecycleView.setLayoutManager(mLayoutManager);
                mRecycleView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                // listeners for grid item clicks
                mAdapter.setOnItemClickListener(new ParkImageAdapter.OnItemClickListener() {

                    // click listener for general grid view touch
                    @Override
                    public void onItemClick(int position) {
                        // create intent and add data extras
                        Intent intent = new Intent(NationalParkListActivity.this, MenuActivity.class);
                        intent.putExtra(EXTRA_RES_ID_1, nationalParkItemList.get(position).getName());
                        intent.putExtra(EXTRA_RES_ID_2, nationalParkItemList.get(position).getParkCode());
                        intent.putExtra(EXTRA_RES_ID_3, nationalParkItemList.get(position).getImageRes());
                        // Start the activity
                        startActivity(intent);
                    }

                    // click listener for bookmark icon touch
                    @Override
                    public void onBookmarkClick(int position) {
                        // decide to fill or un-fill bookmark icon
                        if (nationalParkItemList.get(position).getBookmarkImageRes() == R.drawable.bookmark_filled) { // display un-filled bookmark
                            nationalParkItemList.get(position).setBookmarkImageRes(R.drawable.bookmark_unfilled);
                        }
                        else { // display filled bookmark
                            nationalParkItemList.get(position).setBookmarkImageRes(R.drawable.bookmark_filled);
                        }
                        mAdapter.notifyItemChanged(position); // notify changes made to adapter
                    }
                });

            }
            @Override
            public void onFailure(Call<ParkArray> call, Throwable t) { // display failure message
                Log.i("NationalParkList.java", "FAILURE MESSAGE: " + t.getMessage());
            }
        });

    }

    @Override
    protected void onStart() {
        // make changes to bookmarked icon from bookmarked activity
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged(); // notify changes made to adapter
        }
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // set up menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.national_park_action_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.parkListSearchItem);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // set keyboard done to check mark not search icon
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // do nothing since we query in real time
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // filter in real time
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.parkListBookMarkItem:
                Intent bookmarkIntent = new Intent(this, BookmarkedActivity.class);
                bookmarkIntent.putExtra(EXTRA_RES_ID_4, nationalParkItemList);
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