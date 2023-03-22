package ca.carleton.AmazinBookStore.Book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("12345");
    }

    @Test
    public void testGetTitle() {
        Assertions.assertEquals("Test Book", book.getTitle());
    }

    @Test
    public void testGetIsbn() {
        Assertions.assertEquals("12345", book.getIsbn());
    }

    @Test
    public void testSetTitle() {
        book.setTitle("New Title");
        Assertions.assertEquals("New Title", book.getTitle());
    }

    @Test
    public void testSetIsbn() {
        book.setIsbn("67890");
        Assertions.assertEquals("67890", book.getIsbn());
    }
}
