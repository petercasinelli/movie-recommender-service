package edu.bc.casinepe.api;

import org.codehaus.jackson.annotate.JsonProperty;

public class MovieBean {

    @JsonProperty("movieId")
    private long id;
    private String title;
    private double rating;
    private int ratingsCount;

    public MovieBean() {}

    public MovieBean(long id, String title, double rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public double getRating() {
        return this.rating;
    }

    public int getRatingsCount() { return this.ratingsCount; }


}