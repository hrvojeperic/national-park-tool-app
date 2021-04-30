package com.codingproject.nationalparktool.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.codingproject.nationalparktool.adapterdata.NationalParkItem;
import com.codingproject.nationalparktool.R;
import com.codingproject.nationalparktool.adapters.ParkImageAdapter;

import java.util.ArrayList;

public class BookmarkedActivity extends AppCompatActivity {

    // declare variables for national park recycler view
    private ArrayList<NationalParkItem> nationalParkItemList;
    private ArrayList<NationalParkItem> bookmarkedParkItemList;
    private RecyclerView mRecycleView;
    private ParkImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked);

        // set action bar title
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bookmarked");
        setSupportActionBar(toolbar);

        // get national parks
        nationalParkItemList = (ArrayList<NationalParkItem>) getIntent().getSerializableExtra(NationalParkListActivity.EXTRA_RES_ID_4);
        bookmarkedParkItemList = new ArrayList<>();
        for (int i = 0; i < nationalParkItemList.size(); i++) {
            if (nationalParkItemList.get(i).getBookmarkImageRes() == R.drawable.bookmark_filled) {
                bookmarkedParkItemList.add(nationalParkItemList.get(i));
            }
        }

        // set up recycler view
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(BookmarkedActivity.this);
        mAdapter = new ParkImageAdapter(bookmarkedParkItemList);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        // listeners for grid item clicks
        mAdapter.setOnItemClickListener(new ParkImageAdapter.OnItemClickListener() {

            // click listener for general grid view touch
            @Override
            public void onItemClick(int position) {
                // create intent and add data extras
                Intent intent = new Intent(BookmarkedActivity.this, MenuActivity.class);
                intent.putExtra(NationalParkListActivity.EXTRA_RES_ID_1, bookmarkedParkItemList.get(position).getName());
                intent.putExtra(NationalParkListActivity.EXTRA_RES_ID_2, bookmarkedParkItemList.get(position).getParkCode());
                intent.putExtra(NationalParkListActivity.EXTRA_RES_ID_3, bookmarkedParkItemList.get(position).getImageRes());
                // Start the activity
                startActivity(intent);
            }

            // click listener for bookmark icon touch
            @Override
            public void onBookmarkClick(int position) {
                // decide to fill or un-fill bookmark icon
                if (bookmarkedParkItemList.get(position).getBookmarkImageRes() == R.drawable.bookmark_filled) { // display un-filled bookmark
                    bookmarkedParkItemList.get(position).setBookmarkImageRes(R.drawable.bookmark_unfilled);
                    // find modified park index and change the image resource for it
                    String targetPark = bookmarkedParkItemList.get(position).getParkCode();
                    for (int i = 0; i < NationalParkListActivity.nationalParkItemList.size(); i++) {
                        if (targetPark.equals(NationalParkListActivity.nationalParkItemList.get(i).getParkCode())) {
                            NationalParkListActivity.nationalParkItemList.get(i).setBookmarkImageRes(R.drawable.bookmark_unfilled);
                            break;
                        }
                    }

                }
                else { // display filled bookmark
                    bookmarkedParkItemList.get(position).setBookmarkImageRes(R.drawable.bookmark_filled); // display filled bookmark
                    // find modified park index and change the image resource for it
                    String targetPark = bookmarkedParkItemList.get(position).getParkCode();
                    for (int i = 0; i < NationalParkListActivity.nationalParkItemList.size(); i++) {
                        if (targetPark.equals(NationalParkListActivity.nationalParkItemList.get(i).getParkCode())) {
                            NationalParkListActivity.nationalParkItemList.get(i).setBookmarkImageRes(R.drawable.bookmark_filled);
                            break;
                        }
                    }
                }
                mAdapter.notifyItemChanged(position); // notify changes made to adapter
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // set up menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookmark_activity_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.bookmarkActivitySearchItem);
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

}