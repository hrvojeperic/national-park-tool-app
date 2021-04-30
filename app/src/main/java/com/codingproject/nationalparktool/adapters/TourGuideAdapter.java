package com.codingproject.nationalparktool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codingproject.nationalparktool.adapterdata.TourGuideItem;
import com.codingproject.nationalparktool.R;
import java.util.ArrayList;

public class TourGuideAdapter extends ArrayAdapter<TourGuideItem> {

    // constructor
    public TourGuideAdapter(@NonNull Context context, ArrayList<TourGuideItem> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_tour_guides_listview_item, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        TourGuideItem currentNumberPosition = getItem(position);

        // set name
        TextView textView0 = currentItemView.findViewById(R.id.nameListViewItem);
        textView0.setText(currentNumberPosition.getName());

        // set description
        TextView textView1 = currentItemView.findViewById(R.id.descriptionListViewItem);
        textView1.setText(currentNumberPosition.getDescription());

        // set duration
        TextView textView2 = currentItemView.findViewById(R.id.durationListViewItem);
        textView2.setText(currentNumberPosition.getDuration());

        // set stops
        TextView textView3 = currentItemView.findViewById(R.id.stopsListViewItem);
        textView3.setText(currentNumberPosition.getStops());

        // return
        return currentItemView;
    }
}