package model;

import java.util.Objects;

// represents a type of visual media
public abstract class Media {
    protected String title;
    protected String genres;     // comma-separated list of genres as a string
    protected String cast;       // comma-separated cast list as a string
    protected int releaseYear;
    protected int rating;        // personal rating, ranges from 1-5 stars, default (no rating) is 0

    // EFFECTS: creates a type of visual media w/ a title, list of genres, list of cast, and release year
    public Media(String title, String genres, String cast, int releaseYear) {
        this.title = title;
        this.genres = genres;
        this.cast = cast;
        this.releaseYear = releaseYear;
    }

    // REQUIRES: integer in the interval [1,5]
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public String getCast() {
        return cast;
    }

    public int getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    // MODIFIES: this
    // EFFECTS:  adds another genre to the film's list of genres
    public void addGenre(String genre) {
        genres += ", " + genre;
    }

    // MODIFIES: this
    // EFFECTS:  adds another cast member to the film's cast
    public void addCastMember(String castMember) {
        cast += ", " + castMember;
    }

    // EFFECTS: prints out specific details about media type
    public abstract String details();
}
