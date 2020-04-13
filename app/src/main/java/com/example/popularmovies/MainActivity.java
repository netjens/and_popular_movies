package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieList;
    private RecyclerView.Adapter movieListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar loadingMoviesIndicator;

    public static String MOVIE_DB_API_KEY = "ed1e27120e1394eb9dc0809cc4621160";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList = (RecyclerView) findViewById(R.id.rv_movie_list);
        loadingMoviesIndicator = findViewById(R.id.loading_indicator);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        movieList.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,2);
        movieList.setLayoutManager(layoutManager);


        movieListAdapter = new MovieListAdapter();
        movieList.setAdapter(movieListAdapter);





        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=ed1e27120e1394eb9dc0809cc4621160");
            new MovieQueryTask().execute(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        // COMPLETED (26) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingMoviesIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieResults = null;
            try {
                movieResults = NetworkUtils.getResponseFromHttpsUrl(searchUrl);
            } catch (IOException e) {
                Log.e("MainActivity",e.getMessage(),e);
            }
            return movieResults;
        }

        @Override
        protected void onPostExecute(String movieResults) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            loadingMoviesIndicator.setVisibility(View.INVISIBLE);
            if (movieResults != null && !movieResults.equals("")) {
                // COMPLETED (17) Call showJsonDataView if we have valid, non-null results
                //showJsonDataView();
                //mSearchResultsTextView.setText(githubSearchResults);
                Log.i("MainActivity",movieResults);
            } else {
                // COMPLETED (16) Call showErrorMessage if the result is null in onPostExecute
                Toast.makeText(getParent(),"Error ocurred at query of moviedb",Toast.LENGTH_LONG).show();
            }
        }
    }


}
