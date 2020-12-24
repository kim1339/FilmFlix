package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueTest {
    private Film f1, f2, f3, f4;
    private Show s1, s2;
    private String f1c, f2c, f3c, f4c, f1g, f2g, f3g, s1c, s2c, s1g, s2g;
    private Catalogue cat;

    @BeforeEach
    void runBefore() {
        // setting up example films
        f1g = "Drama";
        f1c = "Willem DaFoe, Brooklyn Prince";
        f1 = new Film("The Florida Project", "Sean Baker", f1g, f1c, 2017);

        f2g = "Disney, Adventure";
        f2c = "Johnny Depp";
        f2 = new Film("Pirates of the Caribbean: The Curse of the Black Pearl",
                "Gore Verbinski", f2g, f2c, 2003);

        f3g = "International, Drama";
        f3c = "Vincent Cassel";
        f3 = new Film("La Haine", "Mathieu Kassovitz", f3g, f3c, 1995);

        f4c = "Johnny Depp";
        f4 = new Film("Pirates of the Caribbean: Dead Man's Chest", "Gore Verbinski",
                f2g, f4c, 2006);

        // setting up example shows
        s1g = "Mystery";
        s1c = "David Suchet, Hugh Fraser";
        s1 = new Show("Poirot", s1g, s1c, 13, 1989);

        s2g = "Sitcom";
        s2c = "Ellie Kemper, Tituss Burgess, Carol Kane";
        s2 = new Show("Unbreakable Kimmy Schmidt", s2g, s2c, 4, 2015);

        cat = new Catalogue("Kimia's catalogue");
    }

    @Test
    void testConstructor() {
        assertEquals(0, cat.getNumShows());
        assertEquals(0, cat.getNumFilms());
    }

    @Test
    void testAddFilm() {
        cat.addFilm(f1);
        cat.addFilm(f2);
        assertEquals(2, cat.getNumFilms());
        assertTrue(cat.getFilms().get(1).getCast().contains("Johnny Depp"));
    }

    @Test
    void testRemoveFilm() {
        cat.addFilm(f1);
        cat.addFilm(f2);
        cat.removeFilm(f1);
        assertEquals(1, cat.getNumFilms());
        assertTrue(cat.getFilms().get(0).getCast().contains("Johnny Depp"));
    }

    @Test
    void testAddShow() {
        cat.addFilm(f1);
        cat.addShow(s1);
        cat.addShow(s2);
        assertEquals(2, cat.getNumShows());
        assertTrue(cat.getShows().get(0).getCast().contains("David Suchet"));
        System.out.println(cat.viewAllShows(cat.getShows()));
        System.out.println("\n"+cat.viewAllFilms(cat.getFilms()));
    }

    @Test
    void testRemoveShow() {
        cat.addShow(s1);
        cat.addShow(s2);
        cat.removeShow(s1);
        assertEquals(1, cat.getNumShows());
    }

    @Test
    void testSearchFilmsByTitle() {
        cat.addFilm(f1);
        cat.addFilm(f2);
        cat.addFilm(f3);

        assertEquals(2, cat.getFilmsByTitle("The").size());
        assertEquals(1, cat.getFilmsByTitle("la haine").size());
        assertEquals(0, cat.getFilmsByTitle("world").size());
    }

    @Test
    void testSearchFilmsByGenre() {
        cat.addFilm(f1);
        cat.addFilm(f2);
        cat.addFilm(f3);

        assertEquals(2, cat.getFilmsByGenre("DRAMA").size());
        assertEquals(1, cat.getFilmsByGenre("disney").size());
        assertEquals(0, cat.getFilmsByGenre("horror").size());
    }

    @Test
    void testSearchFilmsByDate() {
        cat.addFilm(f1);
        cat.addFilm(f2);
        cat.addFilm(f3);
        cat.addFilm(f4);

        assertEquals(1, cat.getFilmsReleasedBefore(1995).size());
        assertEquals(1, cat.getFilmsReleasedBefore(2000).size());
        assertEquals(3, cat.getFilmsReleasedAfter(2000).size());
    }
}