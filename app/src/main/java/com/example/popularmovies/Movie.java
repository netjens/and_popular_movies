package com.example.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Movie implements Parcelable {
    public Movie(int id,  String title,String posterImageId) {
        this.id = id;
        this.posterImageId = posterImageId;
        this.title = title;
    }

    private int id;
    private String posterImageId;
    private String title;


    public int getId() {
        return id;
    }

    public String getPosterImageId() {
        return posterImageId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterImageId);
        dest.writeString(this.title);

    }

    public Movie(Parcel in){
        this.posterImageId = in.readString();
        this.title = in.readString();
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
