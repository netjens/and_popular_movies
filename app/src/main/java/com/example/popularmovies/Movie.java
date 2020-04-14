package com.example.popularmovies;

public class Movie {
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
}
