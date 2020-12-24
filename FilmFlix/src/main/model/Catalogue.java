package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents a catalogue of films and shows
public class Catalogue implements Writable {
    private String name;
    private ArrayList<Film> filmCollection;
    private ArrayList<Show> showCollection;

    // EFFECTS: creates a new catalogue with a film and TV show collection
    public Catalogue(String name) {
        this.name = name;
        filmCollection = new ArrayList<Film>();
        showCollection = new ArrayList<Show>();
    }

    public ArrayList<Film> getFilms() {
        return filmCollection;
    }

    public int getNumFilms() {
        return filmCollection.size();
    }

    public ArrayList<Show> getShows() {
        return showCollection;
    }

    public int getNumShows() {
        return showCollection.size();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds the film to the collection
    public void addFilm(Film film) {
        filmCollection.add(film);
    }

    // MODIFIES: this
    // EFFECTS: removes the film from the collection
    public void removeFilm(Film film) {
        filmCollection.remove(film);
    }

    // MODIFIES: this
    // EFFECTS: adds the show to the collection
    public void addShow(Show show) {
        showCollection.add(show);
    }

    // MODIFIES: this
    // EFFECTS: removes the show from the collection
    public void removeShow(Show show) {
        showCollection.remove(show);
    }

    // EFFECTS: returns the given film collection (and it's size) as a string
    public String viewAllFilms(ArrayList<Film> films) {
        String masterList = "";
        for (Film f: films) {
            masterList += "\n" + f.details();
        }
        return films.size() + " film(s) in collection:\n" + masterList;
    }

    // EFFECTS: returns the given show collection (and it's size) as a string
    public String viewAllShows(ArrayList<Show> shows) {
        String masterList = "";
        for (Show s: shows) {
            masterList += "\n" + s.details();
        }
        return shows.size() + " show(s) in collection:\n" + masterList;
    }

    // some basic search and filter functions for films

    public ArrayList<Film> getFilmsByTitle(String title) {
        ArrayList<Film> results = new ArrayList<Film>();
        for (Film f: filmCollection) {
            if (f.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(f);
            }
        }
        return results;
    }

    public ArrayList<Film> getFilmsByGenre(String genre) {
        ArrayList<Film> results = new ArrayList<Film>();
        for (Film f: filmCollection) {
            if (f.getGenres().toLowerCase().contains(genre.toLowerCase())) {
                results.add(f);
            }
        }
        return results;
    }

    public ArrayList<Film> getFilmsReleasedBefore(int year) {
        ArrayList<Film> results = new ArrayList<Film>();
        for (Film f: filmCollection) {
            if (f.getReleaseYear() <= year) {
                results.add(f);
            }
        }
        return results;
    }

    public ArrayList<Film> getFilmsReleasedAfter(int year) {
        ArrayList<Film> results = new ArrayList<Film>();
        for (Film f: filmCollection) {
            if (f.getReleaseYear() >= year) {
                results.add(f);
            }
        }
        return results;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("film collection", filmsToJson());
        json.put("show collection", showsToJson());
        return json;
    }

    // EFFECTS: returns shows in the catalogue as a JSON array
    private JSONArray showsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Show s : showCollection) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns films in the catalogue as a JSON array
    private JSONArray filmsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Film f : filmCollection) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}
