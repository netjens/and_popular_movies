package com.example.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

class MovieListAdapter extends ArrayAdapter<Movie> {
    private static String BASE_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieListAdapter(Context context ){
        super(context,0,new ArrayList<Movie>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Movie movie = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_list_item, parent, false);
        }

        ImageView movieImage = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load("https://image.tmdb.org/t/p/w185"+movie.getPosterImageId()).into(movieImage);
        TextView movieTitle = convertView.findViewById(R.id.tv_movie_title);
        movieTitle.setText(movie.getTitle());



        return convertView;



    }

}
