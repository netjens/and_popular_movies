package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    public static final String MOVIE_PARCEL_KEY = "movie_key";
    MovieListAdapter movieListAdapter;

    private ProgressBar loadingMoviesIndicator;
    private TextView titleText;


    public static String TOP_RATED = "top_rated";
    public static String MOST_POPULAR = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingMoviesIndicator = findViewById(R.id.loading_indicator);

        movieListAdapter = new MovieListAdapter(this);
        GridView gridView = findViewById(R.id.movie_grid);
        gridView.setAdapter(movieListAdapter);
        gridView.setOnItemClickListener(this);
        titleText = findViewById(R.id.tv_title);
        titleText.setText(R.string.title_popular);
        URL url = NetworkUtils.buildUrl(NetworkUtils.MovieDBQueryType.MOST_POPUAR);
        new MovieQueryTask(this).execute(url);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * When you click the reset menu item, we want to start all over
             * and display the pretty gradient again. There are a few similar
             * ways of doing this, with this one being the simplest of those
             * ways. (in our humble opinion)
             */
            case R.id.mi_popularity:
                URL url = NetworkUtils.buildUrl(NetworkUtils.MovieDBQueryType.MOST_POPUAR);
                new MovieQueryTask(this).execute(url);
                titleText.setText(R.string.title_popular);

                return true;

            case R.id.mi_rating:
                url = NetworkUtils.buildUrl(NetworkUtils.MovieDBQueryType.TOP_RATED);
                new MovieQueryTask(this).execute(url);
                titleText.setText(R.string.title_rating);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = movieListAdapter.getItem(position);
        Toast.makeText(this,"item " +  movie.getTitle() + " clicked",Toast.LENGTH_LONG).show();
        Intent movieDetailIntent = new Intent(this,MovieDetailActivity.class);
        movieDetailIntent.putExtra(MOVIE_PARCEL_KEY, movie);
        startActivity(movieDetailIntent);

    }


    public class MovieQueryTask extends AsyncTask<URL, Void, List<Movie>> {
        private Context context;

        public MovieQueryTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingMoviesIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieResults = null;
            List<Movie> movies = new ArrayList<Movie>();
            try {
                movieResults = NetworkUtils.getResponseFromHttpsUrl(searchUrl);
                JSONObject resultAsJson = new JSONObject(movieResults);
                JSONArray moviesAsJson = resultAsJson.getJSONArray("results");
                for (int i = 0; i < moviesAsJson.length(); i++) {
                    JSONObject movieAsJson = moviesAsJson.getJSONObject(i);
                    movies.add(new Movie(movieAsJson.getInt("id"),
                            movieAsJson.getString("title"),
                            movieAsJson.getString("poster_path")));
                }

            } catch (IOException | JSONException e) {
                Timber.e(e.getMessage(), e);
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movieResults) {
            //movies.clear();
            loadingMoviesIndicator.setVisibility(View.INVISIBLE);
            if (movieResults != null) {
                Timber.d("found " + movieResults.size() + " movies");
                movieListAdapter.clear();
                movieListAdapter.addAll(movieResults);


            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                Toast.makeText(getParent(), "Error ocurred at query of moviedb", Toast.LENGTH_LONG).show();
            }
        }
    }


}
