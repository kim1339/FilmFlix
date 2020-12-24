// Used the following sources for guidance:
// https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
// https://docs.oracle.com/javase/7/docs/api/javax/swing/table/TableModel.html#isCellEditable(int,%20int)
// https://docs.oracle.com/javase/7/docs/api/javax/swing/JTable.html

package ui;

import model.Film;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// represents the FilmFlix application view
public class FilmFlixGUI extends JPanel {

    private JTable filmCollection;
    private DefaultTableModel tableModel;

    private JButton addButton;
    private JButton removeButton;
    private JButton loadButton;
    private JButton saveButton;

    private JTextField newFilmTitle;
    private JTextField newFilmReleaseYear;
    private JTextField newFilmGenres;
    private JTextField newFilmDirector;
    private JTextField newFilmCast;
    private JComboBox newFilmRating;
    private JComboBox<String> searchBy;
    private JTextField searchTerm;

    public JTable getFilmCollection() {
        return filmCollection;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getSearchTerm() {
        return searchTerm;
    }

    public JComboBox<String> getSearchComboBox() {
        return searchBy;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JTextField getNewFilmTitle() {
        return newFilmTitle;
    }

    public JTextField getNewFilmReleaseYear() {
        return newFilmReleaseYear;
    }

    public JTextField getNewFilmGenres() {
        return newFilmGenres;
    }

    public JTextField getNewFilmDirector() {
        return newFilmDirector;
    }

    public JTextField getNewFilmCast() {
        return newFilmCast;
    }

    public JComboBox getNewFilmRating() {
        return newFilmRating;
    }

// TODO: use a Layout Manager instead of absolute positioning, increase size dimensions,
//       add a component that displays the number of films in the collection or search results

    // EFFECTS: creates the FilmFlix view
    public FilmFlixGUI() {
        setLayout(null);
        catalogueLabel();
        setUpTableAndPane();
        newFilmLabelsAndFields();
        addAndRemoveButtons();
        loadAndSaveButtons();
        setUpSearch();
    }

    // MODIFIES: this
    // EFFECTS: creates the search/filter label, field, and combo box
    private void setUpSearch() {
        JLabel searchLabel = new JLabel("Search/Filter Films");
        searchLabel.setLocation(325, 445);
        searchLabel.setSize(150, 25);
        searchLabel.setIcon(loadIcon("search", 20, 20));
        add(searchLabel);

        searchBy = new JComboBox<String>(new String[] { "Title", "Genre", "Director", "Cast"});
        searchBy.setLocation(405, 500);
        searchBy.setSize(105, 25);
        add(searchBy);

        searchTerm = new JTextField();
        searchTerm.setLocation(275, 500);
        searchTerm.setSize(125, 25);
        add(searchTerm);
    }

    // MODIFIES: this
    // EFFECTS: creates the "Load Catalogue" and "Save Catalogue" buttons
    private void loadAndSaveButtons() {
        loadButton = new JButton("Load Catalogue");
        loadButton.setLocation(515, 330);
        loadButton.setSize(150, 30);
        loadButton.setIcon(loadIcon("load", 20, 20));
        add(loadButton);

        saveButton = new JButton("Save Catalogue");
        saveButton.setLocation(515, 370);
        saveButton.setSize(150, 30);
        saveButton.setIcon(loadIcon("save", 20, 20));
        add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the "Add Film" and "Remove Film" buttons
    private void addAndRemoveButtons() {
        addButton = new JButton("Add Film");
        addButton.setLocation(265, 380);
        addButton.setSize(100, 25);
        add(addButton);

        removeButton = new JButton("Remove Film");
        removeButton.setLocation(515, 290);
        removeButton.setSize(150, 30);
        add(removeButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the labels and fields for the new film entry
    private void newFilmLabelsAndFields() {
        title();
        director();
        year();
        rating();
        genresAndCast();
    }

    // MODIFIES: this
    // EFFECTS: creates the labels and fields for the new film genres and cast entries
    private void genresAndCast() {
        JLabel genreLabel = new JLabel("Genres");
        genreLabel.setLocation(10, 315);
        genreLabel.setSize(50, 25);
        add(genreLabel);
        newFilmGenres = new JTextField();
        newFilmGenres.setLocation(10, 335);
        newFilmGenres.setSize(150, 25);
        add(newFilmGenres);

        JLabel castLabel = new JLabel("Cast");
        castLabel.setLocation(10, 360);
        castLabel.setSize(100, 25);
        add(castLabel);
        newFilmCast = new JTextField();
        newFilmCast.setLocation(10, 380);
        newFilmCast.setSize(250, 25);
        add(newFilmCast);
    }

    // MODIFIES: this
    // EFFECTS: creates the label and field for the new film rating entry
    private void rating() {
        JLabel ratingLabel = new JLabel("Rating");
        ratingLabel.setLocation(170, 315);
        ratingLabel.setSize(50, 25);
        add(ratingLabel);
        Integer[] ratings = { 0, 1, 2, 3, 4, 5 };
        newFilmRating = new JComboBox(ratings);
        newFilmRating.setLocation(170, 335);
        newFilmRating.setSize(90, 25);
        add(newFilmRating);
    }

    // MODIFIES: this
    // EFFECTS: creates the label and field for the new film release year entry
    private void year() {
        JLabel yearLabel = new JLabel("Year");
        yearLabel.setLocation(270, 315);
        yearLabel.setSize(50, 25);
        add(yearLabel);
        newFilmReleaseYear = new JTextField();
        newFilmReleaseYear.setLocation(270, 335);
        newFilmReleaseYear.setSize(65, 25);
        add(newFilmReleaseYear);
    }

    // MODIFIES: this
    // EFFECTS: creates the label and field for the new film director entry
    private void director() {
        JLabel directorLabel = new JLabel("Director");
        directorLabel.setLocation(170, 270);
        directorLabel.setSize(75, 25);
        add(directorLabel);
        newFilmDirector = new JTextField();
        newFilmDirector.setLocation(170, 290);
        newFilmDirector.setSize(150, 25);
        add(newFilmDirector);
    }

    // MODIFIES: this
    // EFFECTS: creates the label and field for the new film title entry
    private void title() {
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setLocation(10, 270);
        titleLabel.setSize(50, 25);
        add(titleLabel);
        newFilmTitle = new JTextField();
        newFilmTitle.setLocation(10, 290);
        newFilmTitle.setSize(150, 25);
        add(newFilmTitle);
    }

    // MODIFIES: this
    // EFFECTS: creates table and scroll pane for film catalogue
    private void setUpTableAndPane() {
        tableModel = new DefaultTableModel(new String[] { "Title", "Director", "Genre", "Year", "Rating", "Cast" },
                0);
        filmCollection = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // to prevent table data from being changed
            }
        };
        filmCollection.getTableHeader().setFont(new Font(getFont().getFontName(), Font.BOLD, getFont().getSize()));
        filmCollection.setAutoCreateRowSorter(true); // optional

        JScrollPane pane = new JScrollPane(filmCollection);
        pane.setLocation(10, 65);
        pane.setSize(700, 200);
        add(pane);
    }

    // MODIFIES: this
    // EFFECTS: creates the label and sets the icon for the film catalogue
    private void catalogueLabel() {
        JLabel catalogueLabel = new JLabel("Film Catalogue");
        Font font = catalogueLabel.getFont();
        catalogueLabel.setFont(font.deriveFont(font.BOLD, 23f));
        catalogueLabel.setLocation(275, 10);
        catalogueLabel.setSize(250, 50);
        catalogueLabel.setIcon(loadIcon("icon", 40, 40));
        add(catalogueLabel);
    }

    // EFFECTS: loads and returns an image icon w/ the given file name and dimensions
    private Icon loadIcon(String fileName, int width, int height) {
        // some of the code below was used in UBC CPSC 210's C3-LectureLabStarter repo
        String sep = System.getProperty("file.separator");
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + sep + "data" + sep + fileName
                + ".png");
        Image iconImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(iconImage);
        return resizedIcon;
    }

    // MODIFIES: this
    // EFFECTS: adds films to table
    public void updateCatalogue(ArrayList<Film> filmCollection) {
        tableModel.setRowCount(0);
        if (filmCollection != null) {
            for (Film film : filmCollection) {
                tableModel.addRow(filmToObjectArray(film));
            }
        }
    }

    // EFFECTS: splits the film into it's respective fields
    public static Object[] filmToObjectArray(Film film) {
        Object[] array = new Object[6];
        array[0] = film.getTitle();
        array[1] = film.getDirector();
        array[2] = film.getGenres();
        array[3] = film.getReleaseYear();
        array[4] = film.getRating();
        array[5] = film.getCast();
        return array;
    }

}
