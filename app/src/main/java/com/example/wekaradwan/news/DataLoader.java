package com.example.wekaradwan.news;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by weka radwan on 4/28/2018.
 */

public class DataLoader extends AsyncTaskLoader<List<Data>> {

    /**
     * Query URL
     */
    private String wUrl;

    /**
     * Constructs a new {@link DataLoader}.
     *
     * @param context of the activity.
     * @param url     to loading the data.
     */
    public DataLoader(Context context, String url) {
        super(context);
        wUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Data> loadInBackground() {
        if (wUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of news.
        List<Data> data = QueryUtils.fetchNewsData(wUrl);
        return data;
    }
}
