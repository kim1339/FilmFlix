package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an individual TV show
public class Show extends Media implements Writable {
    private int numSeasons;

    // EFFECTS: creates a Show w/ a title, list of genres, list of cast, number of seasons, and release year
    public Show(String title, String genres, String cast, int numSeasons, int releaseYear) {
        super(title, genres, cast, releaseYear);
        this.numSeasons = numSeasons;
    }

    public int getNumSeasons() {
        return numSeasons;
    }

    @Override
    // EFFECTS: returns all the information about a given show
    public String details() {
        return "Title: " + this.getTitle() + " | No. of Seasons: " + this.getNumSeasons() + " | Genres: "
                + this.getGenres() + " | Start Year: " + this.getReleaseYear() + " | Rating: "
                + this.getRating() + " | Cast: " + this.getCast();
    }

    // EFFECTS: returns Show as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("no. of seasons", numSeasons);
        json.put("genres", genres);
        json.put("release year", releaseYear);
        json.put("rating", rating);
        json.put("cast", cast);
        return json;
    }
}
