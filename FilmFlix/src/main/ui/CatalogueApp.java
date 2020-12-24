// NOTE: some code was modelled after UBC's CPSC 210 TellerApp class and JsonSerialization Demo:
//                      https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

/*
package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// cinema collection application (console-based ui)
public class CatalogueApp {
    private static final String JSON_STORE = "./data/catalogue.json";
    private Catalogue cat;
    private Scanner input;
    private ArrayList<Film> films;
    private ArrayList<Show> shows;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the app
    public CatalogueApp() {
        input = new Scanner(System.in);
        cat = new Catalogue("Kimia's catalogue");
        films = cat.getFilms();
        shows = cat.getShows();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runApp() {
        boolean keepGoing = true;
        System.out.println("\nWelcome to FilmFlix, your personal cinema collection.");
        while (keepGoing) {
            displayMenu();
            String command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Thanks for using FilmFlix! Bye for now!");
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processCommand(String command) {
        switch (command) {
            case "v":
                showFullView();
                break;
            case "f":
                runFilmMenu();
                break;
            case "tv":
                runShowMenu();
                break;
            case "l":
                loadCatalogue();
                break;
            case "s":
                saveCatalogue();
                break;
            default:
                System.out.println("Sorry, selection not valid...");
                break;
        }
    }

    // EFFECTS: saves catalogue to file
    private void saveCatalogue() {
        try {
            jsonWriter.open();
            jsonWriter.write(cat);
            jsonWriter.close();
            System.out.println("Saved " + cat.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads catalogue from file
    private void loadCatalogue() {
        try {
            cat = jsonReader.read();
            films = cat.getFilms();
            shows = cat.getShows();
            System.out.println("Loaded " + cat.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the TV show menu and processes user input
    private void runShowMenu() {
        boolean keepGoing = true;
        while (keepGoing) {
            displayShowMenu();
            String command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processShowCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input relating to TV shows
    private void processShowCommand(String command) {
        input.nextLine();
        if (command.equals("add")) {
            addShow();
        } else if (command.equals("remove")) {
            System.out.println("Please insert the title of the show you want to remove:");
            removeShowByTitle(input.nextLine());
        } else if (command.equals("rating")) {
            System.out.println("Please insert the title of the show you want to rate:");
            setShowRating(input.nextLine());
        } else if (command.equals("add-cast")) {
            System.out.println("Please insert the title of the show:");
            addShowCast(input.nextLine());
        } else {
            System.out.println("Sorry, selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds more cast members to the show w/ the given title
    private void addShowCast(String title) {
        for (Show s: shows) {
            if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
                System.out.println("As a comma-separated list, please insert the cast members you want to add:");
                s.addCastMember(input.nextLine());
                break;
            }
        }
    }

    private void setShowRating(String title) {
        for (Show s: shows) {
            if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
                System.out.println("Please insert an integer rating (1-5):");
                int rating = Integer.parseInt(input.nextLine());
                s.setRating(rating);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes show w/ the given title from the catalogue
    private void removeShowByTitle(String title) {
        for (Show s: shows) {
            if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
                cat.removeShow(s);
                break;
            }
        }
    }

    // REQUIRES: the user must input an integer for the release year and # of seasons
    // MODIFIES: this
    // EFFECTS: adds a new show to the catalogue
    private void addShow() {
        System.out.println("Okay, adding a new show...\nPlease insert title:");
        String title = input.nextLine();
        System.out.println("Please insert a comma-separated list of genre(s):");
        String genres = input.nextLine();
        System.out.println("Please insert a comma-separated list of cast member(s):");
        String cast = input.nextLine();
        System.out.println("Please insert the release year:");
        int releaseYear = Integer.parseInt(input.nextLine());
        System.out.println("Please insert the number of seasons:");
        int numSeasons = Integer.parseInt(input.nextLine());
        cat.addShow(new Show(title, genres, cast, numSeasons, releaseYear));
    }

    // EFFECTS: prints the TV show menu
    private void displayShowMenu() {
        System.out.println("\nPlease select from:");
        System.out.println("\tadd -> to add a new TV show");
        System.out.println("\tremove -> to remove an existing TV show");
        System.out.println("\trating -> to set the rating of an existing TV show");
        System.out.println("\tadd-cast -> to add more cast members to an existing TV show");
        System.out.println("\tq -> to quit show options and go back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: displays film menu and processes user input
    private void runFilmMenu() {
        boolean keepGoing = true;
        while (keepGoing) {
            displayFilmMenu();
            String command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processFilmCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input relating to films
    private void processFilmCommand(String command) {
        input.nextLine();
        if (command.equals("add")) {
            addFilm();
        } else if (command.equals("remove")) {
            System.out.println("Please insert the title of the film you want to remove:");
            removeFilmByTitle(input.nextLine());
        } else if (command.equals("rating")) {
            System.out.println("Please insert the title of the film you want to rate:");
            setFilmRating(input.nextLine());
        } else if (command.equals("add-cast")) {
            System.out.println("Please insert the title of the film:");
            addFilmCast(input.nextLine());
        } else if (command.equals("filter-genre")) {
            System.out.println("Please insert the genre of interest:");
            String genre = input.nextLine();
            System.out.println(cat.viewAllFilms(cat.getFilmsByGenre(genre)));
        } else {
            System.out.println("Sorry, selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds more cast members to the film w/ the given title
    private void addFilmCast(String title) {
        for (Film f: films) {
            if (f.getTitle().toLowerCase().equals(title.toLowerCase())) {
                System.out.println("As a comma-separated list, please insert the cast members you want to add:");
                f.addCastMember(input.nextLine());
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the rating for the film w/ the given title
    private void setFilmRating(String title) {
        for (Film f: films) {
            if (f.getTitle().toLowerCase().equals(title.toLowerCase())) {
                System.out.println("Please insert an integer rating (1-5):");
                int rating = Integer.parseInt(input.nextLine());
                f.setRating(rating);
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes film w/ the given title from the catalogue
    private void removeFilmByTitle(String title) {
        for (Film f: films) {
            if (f.getTitle().toLowerCase().equals(title.toLowerCase())) {
                cat.removeFilm(f);
                break;
            }
        }
    }

    // REQUIRES: the user must input an integer for the release year
    // MODIFIES: this
    // EFFECTS: adds a new film to the catalogue
    private void addFilm() {
        System.out.println("Okay, adding a new film...\nPlease insert title:");
        String title = input.nextLine();
        System.out.println("Please insert the director's name:");
        String director = input.nextLine();
        System.out.println("Please insert a comma-separated list of genre(s):");
        String genres = input.nextLine();
        System.out.println("Please insert a comma-separated list of cast member(s):");
        String cast = input.nextLine();
        System.out.println("Please insert the release year:");
        int releaseYear = Integer.parseInt(input.nextLine());
        cat.addFilm(new Film(title, director, genres, cast, releaseYear));
    }

    // EFFECTS: prints the film menu
    private void displayFilmMenu() {
        System.out.println("\nPlease select from:");
        System.out.println("\tadd -> to add a new film");
        System.out.println("\tremove -> to remove an existing film");
        System.out.println("\trating -> to set the rating of an existing film");
        System.out.println("\tadd-cast -> to add more cast members to an existing film");
        System.out.println("\tfilter-genre -> to filter films by a specific genre");
        System.out.println("\tq -> to quit film options and go back to main menu");
    }

    // EFFECTS: prints the contents of the catalogue
    private void showFullView() {
        System.out.println(cat.viewAllFilms(films));
        System.out.println("\n" + cat.viewAllShows(shows));
    }

    // EFFECTS: prints the main menu
    private void displayMenu() {
        System.out.println("\nPlease select from:");
        System.out.println("\tv -> to view all of your current films and TV shows");
        System.out.println("\tf -> to go to film options");
        System.out.println("\ttv -> to go to TV show options");
        System.out.println("\tl -> to load catalogue from file");
        System.out.println("\ts -> save catalogue to file");
        System.out.println("\tq -> quit");
    }
}
*/
