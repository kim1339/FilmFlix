// NOTE: parts of the code have been modeled after UBC CPSC 210's Json Serialization Demo:
//                  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Catalogue cat = new Catalogue("Kimia's catalogue");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCatalogue() {
        try {
            Catalogue cat = new Catalogue("Kimia's catalogue");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCatalogue.json");
            writer.open();
            writer.write(cat);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCatalogue.json");
            cat = reader.read();
            assertEquals("Kimia's catalogue", cat.getName());
            assertEquals(0, cat.getNumFilms() + cat.getNumShows());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCatalogue() {
        try {
            Catalogue cat = new Catalogue("Kimia's catalogue");
            cat.addFilm(new Film("The Shawshank Redemption", "Frank Darabont", "Drama",
                    "Morgan Freeman, Tim Robbins", 1994));
            cat.addShow(new Show("Poirot", "Mystery", "David Suchet", 13, 1989));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCatalogue.json");
            writer.open();
            writer.write(cat);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCatalogue.json");
            cat = reader.read();
            assertEquals("Kimia's catalogue", cat.getName());
            ArrayList<Film> films = cat.getFilms();
            ArrayList<Show> shows = cat.getShows();
            assertEquals(2, films.size() + shows.size());
            checkMedia("The Shawshank Redemption", "Drama", "Morgan Freeman, Tim Robbins",
                    1994, 0, films.get(0));
            checkMedia("Poirot", "Mystery", "David Suchet",
                    1989, 0, shows.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
