package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.popularmovies.MainActivity.MOVIE_PARCEL_KEY;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent movieIntent = getIntent();
        if(movieIntent.hasExtra(MOVIE_PARCEL_KEY)){
            Movie movie = movieIntent.getParcelableExtra(MOVIE_PARCEL_KEY);

            TextView movieTitle = findViewById(R.id.tv_title);
            movieTitle.setText(movie.getTitle());


        }
    }
}
