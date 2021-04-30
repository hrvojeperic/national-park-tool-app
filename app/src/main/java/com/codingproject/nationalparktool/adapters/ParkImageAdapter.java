package com.codingproject.nationalparktool.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingproject.nationalparktool.adapterdata.NationalParkItem;
import com.codingproject.nationalparktool.R;
import com.codingproject.nationalparktool.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;


public class ParkImageAdapter extends RecyclerView.Adapter<ParkImageAdapter.NationalParkViewHolder> implements Filterable {

    private ArrayList<NationalParkItem> mNationalParkList;
    private ArrayList<NationalParkItem> mNationalParkListFull;
    private OnItemClickListener mListener;

    // interface for custom on click listeners
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onBookmarkClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class NationalParkViewHolder extends RecyclerView.ViewHolder {

        public ImageView parkImage;
        public TextView parkName;
        public ImageView bookMarkImage;
        public View itemView;

        public NationalParkViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.itemView = itemView;
            parkImage = itemView.findViewById(R.id.nationalParkImageView);
            parkName = itemView.findViewById(R.id.nationalParkTextView);
            bookMarkImage = itemView.findViewById(R.id.bookmarkImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            bookMarkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onBookmarkClick(position);
                        }
                    }
                }
            });
        }
    }

    // parameterized constructor
    public ParkImageAdapter(ArrayList<NationalParkItem> list) {
        mNationalParkList = list;
        mNationalParkListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public NationalParkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new NationalParkViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NationalParkViewHolder holder, int position) {
        NationalParkItem currentItem = mNationalParkList.get(position);
        GlideApp.with(holder.itemView.getContext()).load(currentItem.getImageRes()).placeholder(R.drawable.no_image_found).into(holder.parkImage);
        holder.parkName.setText(currentItem.getName());
        holder.bookMarkImage.setImageResource(currentItem.getBookmarkImageRes());
    }

    @Override
    public int getItemCount() {
        return mNationalParkList.size();
    }

    @Override
    public Filter getFilter() {
        return nationalParkFilter;
    }

    private Filter nationalParkFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NationalParkItem> filteredList = new ArrayList<>();
            if (constraint.length() == 0 || constraint == null) {
                filteredList.addAll(mNationalParkListFull);
            }
            else {
                String filterString = constraint.toString().toLowerCase().trim();
                for(NationalParkItem park : mNationalParkListFull) {
                    if (park.getName().toLowerCase().contains(filterString)) {
                        filteredList.add(park);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mNationalParkList.clear();
            mNationalParkList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}