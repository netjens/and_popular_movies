package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.popularmovies.MainActivity.MOVIE_PARCEL_KEY;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent movieIntent = getIntent();
        if(movieIntent.hasExtra(MOVIE_PARCEL_KEY)){
            Movie movie = movieIntent.getParcelableExtra(MOVIE_PARCEL_KEY);

            TextView movieTitleView = findViewById(R.id.tv_title);
            movieTitleView.setText(movie.getTitle());
            TextView releaseYearView = findViewById(R.id.tv_release_date);
            releaseYearView.setText("Release Year: "+movie.getReleaseYear());
            TextView voteAverageView = findViewById(R.id.tv_vote);
            voteAverageView.setText("Rating: " +movie.getVoteAverage());
            TextView plotOverviewView = findViewById(R.id.tv_plot);
            plotOverviewView.setText(movie.getPlotOverview());

            ImageView movieImage = findViewById(R.id.iv_movie_poster);
            Picasso.get().setLoggingEnabled(true);
            Picasso.get().load("https://image.tmdb.org/t/p/w342"+movie.getPosterImageId()).into(movieImage);


        }
    }
}
