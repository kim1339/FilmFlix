// Used the following sources for guidance on implementing the search/filter functionality:
// https://stackoverflow.com/questions/20413910/how-can-i-add-a-keylistener-to-my-gui-in-java
// http://www.java2s.com/Code/Java/Swing-JFC/UsearegexFiltertofiltertablecontent.htm
// https://www.codota.com/code/java/methods/javax.swing.RowFilter/regexFilter
// https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#sorting

package ui;

import model.Catalogue;
import model.Film;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// represents the FilmFlix application
public class FilmFlixApp extends JFrame {

    private static final String JSON_STORE = "./data/catalogue.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private FilmFlixGUI view;
    private Catalogue cat;
    private ArrayList<Film> films;

    // MODIFIES: this
    // EFFECTS: loads the film collection from file
    private void loadCatalogue() {
        try {
            cat = new Catalogue("Kimia's catalogue");
            jsonReader = new JsonReader(JSON_STORE);
            cat = jsonReader.read();
            films = cat.getFilms();
            view.updateCatalogue(films);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: writes the film collection to file
    private void saveCatalogue() {
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(cat);
            jsonWriter.close();
            System.out.println("Saved " + cat.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: creates a FilmFlix application w/ the given title and empty catalogue
    public FilmFlixApp(String title) {
        super(title);
        cat = new Catalogue("New Catalogue");
        films = cat.getFilms();
        view = new FilmFlixGUI();
        view.updateCatalogue(films);
        getContentPane().add(view);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initButtons();
    }

    // MODIFIES: view
    // EFFECTS: add action listeners to buttons
    private void initButtons() {

        view.getAddButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addButtonPressed();
            }
        });

        view.getLoadButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadButtonPressed();
            }
        });

        view.getSaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveButtonPressed();
            }
        });

        view.getRemoveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeButtonPressed();
            }
        });

        initSearch();
    }

    // MODIFIES: view
    // EFFECTS: add key listener to search bar
    private void initSearch() {
        view.getSearchTerm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchTerm = view.getSearchTerm().getText().toLowerCase();
                String searchBy = view.getSearchComboBox().getSelectedItem().toString();
                filterFilms(searchBy, searchTerm);
            }
        });
    }

    // MODIFIES: view
    // EFFECTS: filter table rows based on specified column values
    private void filterFilms(String searchBy, String searchTerm) {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<DefaultTableModel>(view.getTableModel());
        view.getFilmCollection().setRowSorter(rowSorter);

        switch (searchBy) {
            case ("Title"):
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm, 0));
                break;
            case ("Director"):
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm, 1));
                break;
            case ("Genre"):
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm, 2));
                break;
            case ("Cast"):
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm, 5));
        }
    }

    // MODIFIES: this
    // EFFECTS: if new film data is valid, adds film to catalogue and resets new film fields
    private void addButtonPressed() {
        if (isNewFilmValid()) {
            films.add(getNewFilm());
            resetNewFilmFields();
            view.updateCatalogue(films);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads film catalogue from file
    private void loadButtonPressed() {
        loadCatalogue();
    }

    // MODIFIES: this
    // EFFECTS: saves film catalogue to file
    private void saveButtonPressed() {
        saveCatalogue();
    }

    // REQUIRES: film must be listed in the original order before removing a film
    //           in other words, should not remove a film after sorting the rows in a different manner
    // MODIFIES: this
    // EFFECTS: removes the selected film from the catalogue
    private void removeButtonPressed() {
        int index = view.getFilmCollection().getSelectedRow();

        // if a row is selected
        if (index != -1) {
            films.remove(index);
            view.updateCatalogue(films);
        }
    }

    // MODIFIES: view
    // EFFECTS: resets the new film fields
    private void resetNewFilmFields() {
        view.getNewFilmTitle().setText("");
        view.getNewFilmDirector().setText("");
        view.getNewFilmCast().setText("");
        view.getNewFilmGenres().setText("");
        view.getNewFilmReleaseYear().setText("");
        view.getNewFilmRating().setSelectedIndex(0);
    }

    private Film getNewFilm() {
        int releaseYear = Integer.parseInt(view.getNewFilmReleaseYear().getText());
        Film newFilm = new Film(view.getNewFilmTitle().getText(), view.getNewFilmDirector().getText(),
                view.getNewFilmGenres().getText(), view.getNewFilmCast().getText(), releaseYear);
        int rating = Integer.valueOf(view.getNewFilmRating().getSelectedItem().toString());
        newFilm.setRating(rating);
        return newFilm;
    }

    // EFFECTS: returns true if all the new film fields have been filled in
    private boolean isNewFilmValid() {
        if (isValid(view.getNewFilmTitle())) {
            if (isValid(view.getNewFilmReleaseYear())) {
                if (isValid(view.getNewFilmGenres())) {
                    if (isValid(view.getNewFilmDirector())) {
                        if (isValid(view.getNewFilmCast())) {
                            if (!(this.view.getNewFilmRating().getSelectedItem().toString() == "")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // EFFECTS: returns true if text field is not empty
    public boolean isValid(JTextField textField) {
        return !textField.getText().isEmpty();
    }
}
