package ca.carleton.AmazinBookStore.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.carleton.AmazinBookStore.Author.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorTest {

    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
    }

    @Test
    public void testId() {
        author.setId(1L);
        assertEquals(1L, author.getId().longValue());
    }

    @Test
    public void testFirstName() {
        author.setFirstName("John");
        assertEquals("John", author.getFirstName());
    }

    @Test
    public void testLastName() {
        author.setLastName("Doe");
        assertEquals("Doe", author.getLastName());
    }

    @Test
    public void testAuthorCreation() {
        assertNotNull(author);
    }
}
