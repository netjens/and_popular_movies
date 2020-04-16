package com.example.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Movie implements Parcelable {
    public Movie(int id,  String title,String posterImageId,int releaseYear,String voteAverage,String plotOverview) {
        this.id = id;
        this.posterImageId = posterImageId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.voteAverage = voteAverage;
        this.plotOverview = plotOverview;
    }

    private int id;
    private String posterImageId;
    private String title;
    private int releaseYear;
    private String voteAverage;
    private String plotOverview;


    public int getId() {
        return id;
    }

    public String getPosterImageId() {
        return posterImageId;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPlotOverview() {
        return plotOverview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterImageId);
        dest.writeString(this.title);
        dest.writeInt(this.releaseYear);
        dest.writeString(this.voteAverage);
        dest.writeString(this.plotOverview);


    }

    public Movie(Parcel in){
        this.posterImageId = in.readString();
        this.title = in.readString();
        this.releaseYear = in.readInt();
        this.voteAverage = in.readString();
        this.plotOverview = in.readString();
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
