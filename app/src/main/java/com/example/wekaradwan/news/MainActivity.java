package com.example.wekaradwan.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Data>> {

    private static final int NEWS_LOADER_ID = 1;

    private static final String USGS_REQUEST_URL =
            "https://content.guardianapis.com/search?q=debate";

    private DataAdapter mAdapter;

    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of News.
        mAdapter = new DataAdapter(this, new ArrayList<Data>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyTextView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Data currentNews = mAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getmUrl());
                // Create a new intent to view the News URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            emptyTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Data>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_newest_value));
        String sectionType = sharedPrefs.getString(
                getString(R.string.settings_section_type_key),
                getString(R.string.settings_section_type_politics_value));

        Uri uri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = uri.buildUpon();
        if (!sectionType.isEmpty()) {
            uriBuilder.appendQueryParameter("section", sectionType);
            uriBuilder.appendQueryParameter("order-by", orderBy);
            uriBuilder.appendQueryParameter("show-tags", "contributor");
            uriBuilder.appendQueryParameter("api-key", "test");
        }
        return new DataLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Data>> loader, List<Data> news) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No News found."
        emptyTextView.setText(R.string.no_news);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Data>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Menu icon On click on it open setting
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}