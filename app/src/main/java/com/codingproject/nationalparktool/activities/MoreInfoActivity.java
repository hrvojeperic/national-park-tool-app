package com.codingproject.nationalparktool.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.codingproject.nationalparktool.constants.ApiKey;
import com.codingproject.nationalparktool.moreinfodata.MoreInfo;
import com.codingproject.nationalparktool.api.NationalParkServiceApi;
import com.codingproject.nationalparktool.R;

import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoreInfoActivity extends AppCompatActivity {

    // declare class variables
    private String parkCode;
    private String parkName;
    private Hashtable<String, String> standardHrsDict;
    private Toolbar toolbar;
    private String contactType;
    private String contactNumber;
    private final String CONTACT_TYPE = "Voice";
    private String address;

    // declare gui variables
    private TextView sunTextView;
    private TextView monTextView;
    private TextView tuesTextView;
    private TextView wedTextView;
    private TextView thurTextView;
    private TextView friTextView;
    private TextView satTextView;
    private TextView addressTextView;
    private Button callNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        // get intent data stored and extras
        Intent intent = getIntent();
        parkCode = intent.getStringExtra(MenuActivity.EXTRA_RES_ID_1);
        parkName = intent.getStringExtra(MenuActivity.EXTRA_RES_ID_2);

        // set action bar title
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(parkName);
        setSupportActionBar(toolbar);

        // get ui elements
        TextView sunTextView = (TextView) findViewById(R.id.sunTextView);
        TextView monTextView = (TextView) findViewById(R.id.monTextView);
        TextView tuesTextView = (TextView) findViewById(R.id.tuesTextView);
        TextView wedTextView = (TextView) findViewById(R.id.wedTextView);
        TextView thurTextView = (TextView) findViewById(R.id.thurTextView);
        TextView friTextView = (TextView) findViewById(R.id.friTextView);
        TextView satTextView = (TextView) findViewById(R.id.satTextView);
        TextView addressTextView = (TextView) findViewById(R.id.addressTextView);
        Button callNowButton = (Button) findViewById(R.id.callNowButton);

        // set up the api request to be called
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developer.nps.gov/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NationalParkServiceApi nationalParkServiceApi = retrofit.create(NationalParkServiceApi.class);
        Call<MoreInfo> call = nationalParkServiceApi.getHours(parkCode, ApiKey.API_KEY);

        // retrofit will create sub-thread to handle API requests
        call.enqueue(new Callback<MoreInfo>() {
            @Override
            public void onResponse(Call<MoreInfo> call, Response<MoreInfo> response) {
                if (!response.isSuccessful()) { // display error codes
                    Log.i("MoreInfoActivity.java", "ERROR CODE: " + response.code());
                    return;
                }
                // build more info object array
                MoreInfo moreInfo = response.body();

                // initialize dictionary to hold api data
                if (moreInfo.getData() != null && moreInfo.getData().get(0).getOperatingHours().size() != 0) {
                    standardHrsDict = new Hashtable<String, String>();
                    standardHrsDict.put("sunday", moreInfo.getData().get(0).getOperatingHours().get(0).getStandardHours().getSunday());
                    standardHrsDict.put("monday", moreInfo.getData().get(0).getOperatingHours().get(0).getStandardHours().getMonday());
                    standardHrsDict.put("tuesday", moreInfo.getData().get(0).getOperatingHours().get(0).getStandardHours().getTuesday());
                    standardHrsDict.put("wednesday", moreInfo.getData().get(0).getOperatingHours().get(0).getStandardHours().getWednesday());
                    standardHrsDict.put("thursday", moreInfo.getData().get(0).getOperatingHours().get(0).getStandardHours().getThursday());
                    standardHrsDict.put("friday", moreInfo.getData().get(0).getOperatingHours().get(0).getStandardHours().getFriday());
                    standardHrsDict.put("saturday", moreInfo.getData().get(0).getOperatingHours().get(0).getStandardHours().getSaturday());
                    // display hours to ui
                    sunTextView.setText(standardHrsDict.get("sunday"));
                    monTextView.setText(standardHrsDict.get("monday"));
                    tuesTextView.setText(standardHrsDict.get("tuesday"));
                    wedTextView.setText(standardHrsDict.get("wednesday"));
                    thurTextView.setText(standardHrsDict.get("thursday"));
                    friTextView.setText(standardHrsDict.get("friday"));
                    satTextView.setText(standardHrsDict.get("saturday"));
                }
                else { // no data available
                    // display hours to ui
                    sunTextView.setText("Unavailable");
                    monTextView.setText("Unavailable");
                    tuesTextView.setText("Unavailable");
                    wedTextView.setText("Unavailable");
                    thurTextView.setText("Unavailable");
                    friTextView.setText("Unavailable");
                    satTextView.setText("Unavailable");
                }
                // display address to ui
                if(moreInfo.getData() != null && moreInfo.getData().get(0).getAddresses().size() != 0) {
                    address = moreInfo.getData().get(0).getAddresses().get(0).getLine1() + " "
                            + moreInfo.getData().get(0).getAddresses().get(0).getLine2() + " "
                            + moreInfo.getData().get(0).getAddresses().get(0).getLine3() + "\n"
                            + moreInfo.getData().get(0).getAddresses().get(0).getCity() + ", "
                            + moreInfo.getData().get(0).getAddresses().get(0).getStateCode() + " "
                            + moreInfo.getData().get(0).getAddresses().get(0).getPostalCode() + "\n";
                    addressTextView.setText(address);
                }
                else { // no data
                    addressTextView.setText("Address Unavailable");
                    addressTextView.setEnabled(false);
                }
                // get contact number
                if (moreInfo.getData() != null && moreInfo.getData().get(0).getContacts().getPhoneNumbers().size() != 0) {
                    contactType = moreInfo.getData().get(0).getContacts().getPhoneNumbers().get(0).getType();
                    contactNumber = moreInfo.getData().get(0).getContacts().getPhoneNumbers().get(0).getPhoneNumber();
                }
                else { // no data
                    contactType = "Unavailable";
                }
            }
                @Override
                public void onFailure(Call<MoreInfo> call, Throwable t) { // display failure message
                    Log.i("MoreInfoActivity.java", "FAILURE MESSAGE: " + t.getMessage());
                }
        });

        // event listener for calling the park
        callNowButton.setOnClickListener(v -> {
            if (contactType.equals(CONTACT_TYPE)) { // its a voice number
                // start a dial intent with number
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contactNumber));
                startActivity(callIntent);
            }
            else { // error checking
                Toast.makeText(this, "Contact Unavailable", Toast.LENGTH_LONG).show();
            }
        });

        // event listener for address directions
        addressTextView.setOnClickListener(v -> {
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+address));
            startActivity(mapIntent);
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