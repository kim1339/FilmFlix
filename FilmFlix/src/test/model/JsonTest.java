package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMedia (String title, String genres, String cast, int year, int rating, Media media) {
        assertEquals(title, media.getTitle());
        assertEquals(genres, media.getGenres());
        assertEquals(cast, media.getCast());
        assertEquals(year, media.getReleaseYear());
        assertEquals(rating, media.getRating());
    }
}
