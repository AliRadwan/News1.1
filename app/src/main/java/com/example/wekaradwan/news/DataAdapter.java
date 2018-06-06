package com.example.wekaradwan.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by weka radwan on 4/28/2018.
 */

public class DataAdapter extends ArrayAdapter<Data> {

    public DataAdapter(Context context, List<Data> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }

        // Make object from the NewsData class and set to it the position.
        Data currentNews = getItem(position);

        // Connect the TextView title with it's ID .
        TextView titleView = (TextView) listItemView.findViewById(R.id.tvTitle);
        titleView.setText(currentNews.getTitle());

        // Connect the TextView information with it's ID .
        TextView informationView = (TextView) listItemView.findViewById(R.id.tvInformations);
        informationView.setText(currentNews.getmInformation());

        // Connect the TextView authorName with it's ID .
        TextView authorNameView = (TextView) listItemView.findViewById(R.id.tvAuthorName);
        authorNameView.setText(currentNews.getAuthorName());

        // Connect the TextView date with it's ID .
        TextView dateView = (TextView) listItemView.findViewById(R.id.tvDate);
        dateView.setText(currentNews.getDate());

        return listItemView;
    }
}