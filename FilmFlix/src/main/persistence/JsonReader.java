// NOTE: parts of the code have been modeled after UBC CPSC 210's Json Serialization Demo:
//                  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// represents a reader that reads JSON data from source file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads catalogue from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public Catalogue read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCatalogue(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses catalogue from JSON object and returns it
    private Catalogue parseCatalogue(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Catalogue c = new Catalogue(name);
        addCollections(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses films and shows from JSON object and adds them to catalogue
    private void addCollections(Catalogue c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("film collection");
        for (Object json : jsonArray) {
            JSONObject nextFilm = (JSONObject) json;
            addFilm(c, nextFilm);
        }
        jsonArray = jsonObject.getJSONArray("show collection");
        for (Object json : jsonArray) {
            JSONObject nextShow = (JSONObject) json;
            addShow(c, nextShow);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses show from JSON object and adds it to catalogue
    private void addShow(Catalogue c, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int numSeasons = jsonObject.getInt("no. of seasons");
        String genres = jsonObject.getString("genres");
        int year = jsonObject.getInt("release year");
        int rating = jsonObject.getInt("rating");
        String cast = jsonObject.getString("cast");

        Show show = new Show(title, genres, cast, numSeasons, year);
        show.setRating(rating);
        c.addShow(show);
    }

    // MODIFIES: c
    // EFFECTS: parses film from JSON object and adds it to catalogue
    private void addFilm(Catalogue c, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String director = jsonObject.getString("director");
        String genres = jsonObject.getString("genres");
        int year = jsonObject.getInt("release year");
        int rating = jsonObject.getInt("rating");
        String cast = jsonObject.getString("cast");

        Film film = new Film(title, director, genres, cast, year);
        film.setRating(rating);
        c.addFilm(film);
    }
}
