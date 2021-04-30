package com.codingproject.nationalparktool.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingproject.nationalparktool.glide.GlideApp;
import com.codingproject.nationalparktool.R;

public class MenuActivity extends AppCompatActivity {

    // declare variables
    private String parkName;
    private String parkCode;
    private String parkImage;
    private Toolbar toolbar;
    CardView historyCard;
    CardView mapCard;
    CardView activitiesCard;
    CardView tourGuidesCard;
    CardView servicesCard;
    CardView moreInfoCard;

    // keys for extras in intent
    protected static final String EXTRA_RES_ID_1 = "PARK_CODE";
    protected static final String EXTRA_RES_ID_2 = "PARK_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // get intent data stored and extras
        Intent intent = getIntent();
        parkName = intent.getStringExtra(NationalParkListActivity.EXTRA_RES_ID_1);
        parkCode = intent.getStringExtra(NationalParkListActivity.EXTRA_RES_ID_2);
        parkImage = intent.getStringExtra(NationalParkListActivity.EXTRA_RES_ID_3);

        // set action bar title
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(parkName);
        setSupportActionBar(toolbar);

        // get gui elements
        historyCard = findViewById(R.id.historyCard);
        mapCard = findViewById(R.id.mapCard);
        activitiesCard = findViewById(R.id.activitiesCard);
        tourGuidesCard = findViewById(R.id.tourGuidesCard);
        servicesCard = findViewById(R.id.servicesCard);
        moreInfoCard = findViewById(R.id.moreInfoCard);

        TextView t1 = findViewById(R.id.titleTextView);
        ImageView t3 = findViewById(R.id.parkImage);
        t1.setText(parkName);
        GlideApp.with(this).load(parkImage).placeholder(R.drawable.no_image_found).into(t3);

        // event listeners for menu options...
        historyCard.setOnClickListener(v -> { // history clicked
            // create intent and add data extras
            Intent historyIntent = new Intent(MenuActivity.this, HistoryActivity.class);
            historyIntent.putExtra(EXTRA_RES_ID_1, parkCode);
            historyIntent.putExtra(EXTRA_RES_ID_2, parkName);
            // Start the activity
            startActivity(historyIntent);
        });

        mapCard.setOnClickListener(v -> { // map clicked
            // create intent and add data extras
            Intent mapIntent = new Intent(MenuActivity.this, MapActivity.class);
            mapIntent.putExtra(EXTRA_RES_ID_1, parkCode);
            mapIntent.putExtra(EXTRA_RES_ID_2, parkName);
            // Start the activity
            startActivity(mapIntent);
        });

        activitiesCard.setOnClickListener(v -> { // activities clicked
            // create intent and add data extras
            Intent activitiesIntent = new Intent(MenuActivity.this, ActivitiesActivity.class);
            activitiesIntent.putExtra(EXTRA_RES_ID_1, parkCode);
            activitiesIntent.putExtra(EXTRA_RES_ID_2, parkName);
            // Start the activity
            startActivity(activitiesIntent);
        });

        tourGuidesCard.setOnClickListener(v -> { // tour guides clicked
            // create intent and add data extras
            Intent tourGuidesIntent = new Intent(MenuActivity.this, TourGuidesActivity.class);
            tourGuidesIntent.putExtra(EXTRA_RES_ID_1, parkCode);
            tourGuidesIntent.putExtra(EXTRA_RES_ID_2, parkName);
            // Start the activity
            startActivity(tourGuidesIntent);
        });

        servicesCard.setOnClickListener(v -> { // services clicked
            // create intent and add data extras
            Intent servicesIntent = new Intent(MenuActivity.this, ServicesActivity.class);
            servicesIntent.putExtra(EXTRA_RES_ID_1, parkCode);
            servicesIntent.putExtra(EXTRA_RES_ID_2, parkName);
            // Start the activity
            startActivity(servicesIntent);
        });

        moreInfoCard.setOnClickListener(v -> { // more info clicked
            // create intent and add data extras
            Intent moreInfoIntent = new Intent(MenuActivity.this, MoreInfoActivity.class);
            moreInfoIntent.putExtra(EXTRA_RES_ID_1, parkCode);
            moreInfoIntent.putExtra(EXTRA_RES_ID_2, parkName);
            // Start the activity
            startActivity(moreInfoIntent);
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