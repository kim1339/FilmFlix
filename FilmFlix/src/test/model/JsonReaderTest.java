// NOTE: parts of the code have been modeled after UBC CPSC 210's Json Serialization Demo:
//                  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Catalogue cat = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCatalogue() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCatalogue.json");
        try {
            Catalogue cat = reader.read();
            assertEquals("Kimia's catalogue", cat.getName());
            assertEquals(0, cat.getNumFilms() + cat.getNumShows());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCatalogue() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCatalogue.json");
        try {
            Catalogue cat = reader.read();
            assertEquals("Kimia's catalogue", cat.getName());
            ArrayList<Film> films = cat.getFilms();
            ArrayList<Show> shows = cat.getShows();
            assertEquals(2, cat.getNumFilms() + cat.getNumShows());
            checkMedia("The Florida Project", "Drama, Indie", "Willem DaFoe, Brooklynn Prince",
                    2017, 5, films.get(0));
            assertEquals("Sean Baker", films.get(0).getDirector());
            checkMedia("Poirot", "Mystery, Crime", "David Suchet, Hugh Fraser, Philip Jackson",
                    1989, 0, shows.get(0));
            assertEquals(13, shows.get(0).getNumSeasons());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
