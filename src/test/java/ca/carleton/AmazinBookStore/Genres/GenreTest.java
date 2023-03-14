package ca.carleton.AmazinBookStore.Genres;

import ca.carleton.AmazinBookStore.Genre.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenreTest {

    private Genre genre;

    @BeforeEach
    public void setUp() {
        genre = new Genre();
        genre.setName("Action");
    }

    @Test
    public void testGetTitle() {
        Assertions.assertEquals("Action", genre.getName());
    }
    @Test
    public void testSetTitle() {
        genre.setName("Horror");
        Assertions.assertEquals("Horror", genre.getName());
    }
}

