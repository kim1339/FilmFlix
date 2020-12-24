package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an individual film
public class Film extends Media implements Writable {
    private String director;

    // EFFECTS: creates a Film w/ a title, director, list of genres, list of cast, and release year
    public Film(String title, String director, String genres, String cast, int releaseYear) {
        super(title, genres, cast, releaseYear);
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    @Override
    // EFFECTS: returns all the information about a given film
    public String details() {
        return "Title: " + this.getTitle() + " | Director: " + this.getDirector() + " | Genres: "
                + this.getGenres() + " | Year: " + this.getReleaseYear() + " | Rating: "
                + this.getRating() + " | Cast: " + this.getCast();
    }

    // EFFECTS: returns Film as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("director", director);
        json.put("genres", genres);
        json.put("release year", releaseYear);
        json.put("rating", rating);
        json.put("cast", cast);
        return json;
    }
}
