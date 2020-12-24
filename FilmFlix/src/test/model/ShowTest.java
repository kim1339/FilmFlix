package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShowTest {
    private Show testShow;
    private String testGenres, testCast;

    @BeforeEach
    void runBefore() {
        testGenres = "Mystery";
        testCast = "David Suchet, Hugh Fraser";
        testShow = new Show("Poirot", testGenres, testCast, 13, 1989);
    }

    @Test
    void testConstructor() {
        assertEquals("Poirot", testShow.getTitle());
        assertEquals(1989, testShow.getReleaseYear());
        assertEquals(testCast, testShow.getCast());
        assertEquals(testGenres, testShow.getGenres());
        assertEquals(13, testShow.getNumSeasons());
    }

    @Test
    void testAddItem(){
        testShow.addGenre("Crime");
        assertEquals("Mystery, Crime", testShow.getGenres());

        testShow.addCastMember("Philip Jackson");
        assertEquals("David Suchet, Hugh Fraser, Philip Jackson", testShow.getCast());
    }

    @Test
    void testRating() {
        assertEquals(0, testShow.getRating());
        testShow.setRating(5);
        assertEquals(5, testShow.getRating());
    }
}
