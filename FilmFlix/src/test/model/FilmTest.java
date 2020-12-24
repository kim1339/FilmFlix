package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private Film testFilm;
    private String testGenres, testCast;

    @BeforeEach
    void runBefore() {
        testGenres = "Drama";
        testCast = "Willem DaFoe, Brooklynn Prince";
        testFilm = new Film("The Florida Project", "Sean Baker", testGenres, testCast, 2017);
    }

    @Test
    void testConstructor() {
        assertEquals("The Florida Project", testFilm.getTitle());
        assertEquals(2017, testFilm.getReleaseYear());
        assertEquals(testCast, testFilm.getCast());
        assertEquals(testGenres, testFilm.getGenres());
        assertEquals("Sean Baker", testFilm.getDirector());
    }

    @Test
    void testAddItem(){
        testFilm.addGenre("Indie");
        assertEquals("Drama, Indie", testFilm.getGenres());

        testFilm.addCastMember("Bria Vinaite");
        assertEquals("Willem DaFoe, Brooklynn Prince, Bria Vinaite", testFilm.getCast());
    }

    @Test
    void testRating() {
        testFilm.setRating(5);
        assertEquals(5, testFilm.getRating());
    }
}