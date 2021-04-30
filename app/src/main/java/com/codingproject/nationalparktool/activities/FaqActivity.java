package com.codingproject.nationalparktool.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.codingproject.nationalparktool.R;

public class FaqActivity extends AppCompatActivity {

    // declare gui variables
    TextView question1TextView, question2TextView, question3TextView, question4TextView, question5TextView, question6TextView, question7TextView, question8TextView, question9TextView, question10TextView;
    TextView answer1TextView, answer2TextView, answer3TextView, answer4TextView, answer5TextView, answer6TextView, answer7TextView, answer8TextView, answer9TextView, answer10TextView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        // set action bar title
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FAQ");
        setSupportActionBar(toolbar);

        // get gui elements
        question1TextView = findViewById(R.id.question1TextView);
        question2TextView = findViewById(R.id.question2TextView);
        question3TextView = findViewById(R.id.question3TextView);
        question4TextView = findViewById(R.id.question4TextView);
        question5TextView = findViewById(R.id.question5TextView);
        question6TextView = findViewById(R.id.question6TextView);
        question7TextView = findViewById(R.id.question7TextView);
        question8TextView = findViewById(R.id.question8TextView);
        question9TextView = findViewById(R.id.question9TextView);
        question10TextView = findViewById(R.id.question10TextView);
        answer1TextView = findViewById(R.id.answer1TextView);
        answer2TextView = findViewById(R.id.answer2TextView);
        answer3TextView = findViewById(R.id.answer3TextView);
        answer4TextView = findViewById(R.id.answer4TextView);
        answer5TextView = findViewById(R.id.answer5TextView);
        answer6TextView = findViewById(R.id.answer6TextView);
        answer7TextView = findViewById(R.id.answer7TextView);
        answer8TextView = findViewById(R.id.answer8TextView);
        answer9TextView = findViewById(R.id.answer9TextView);
        answer10TextView = findViewById(R.id.answer10TextView);

        question1TextView.setText("Do you have Customer Service?");
        answer1TextView.setText("Yes there is customer service, you can contact us.");

        question2TextView.setText("How to bookmark my favorite park?");
        answer2TextView.setText("When you are in the list of parks, each park listed will have a bookmark icon in the corner of the image.");

        question3TextView.setText("How can I view my bookmarked parks?");
        answer3TextView.setText("Click on the options menu in the top right part of the screen and select the “Bookmarked” menu item.");

        question4TextView.setText("Can you contact the park?");
        answer4TextView.setText("You can contact the park by going into the \"More Info\" section after selecting a park.  There you will find a button to Call Now!");

        question5TextView.setText("Where can I find what services are provided by a park?");
        answer5TextView.setText("When you select a park, there is a \"Services\" menu option which lists all the services provided by the park.");

        question6TextView.setText("Is there a tour guide available for all the parks?");
        answer6TextView.setText("When selecting a park there is a \"Tour Guides\" menu option that will give you more information on tours.  If there is no tour guides available it will tell you.");

        question7TextView.setText("Where can I find what time a park open or close?");
        answer7TextView.setText("When you select a park, there is \"More Info\" option menu with the hours available everyday.");

        question8TextView.setText("Where can I view a park’s hours of operation?");
        answer8TextView.setText("Under the “More Info” category on the corresponding park page.");

        question9TextView.setText("How to book the services listed for each parks?");
        answer9TextView.setText("Contact the number listed under more information option to book the services.");

        question10TextView.setText(" How can I view the activities available at a park?");
        answer10TextView.setText("Click on the image of the park you want to visit. Once on the park page, select the “Activities” category.");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // set up menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.faq_activity_action_bar, menu);
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