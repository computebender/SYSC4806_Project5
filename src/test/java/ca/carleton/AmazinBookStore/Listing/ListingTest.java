package ca.carleton.AmazinBookStore.Listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.carleton.AmazinBookStore.Author.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListingTest {

    private Listing listing;

    @BeforeEach
    public void setUp() {
        listing = new Listing();
    }

    @Test
    public void testId() {
        listing.setId(1L);
        assertEquals(1L, listing.getId().longValue());
    }

    @Test
    public void testLocation() {
        listing.setLocation("2");
        assertEquals("2", listing.getLocation());
    }

    @Test
    public void testPrice() {
        listing.setPrice("$20");
        assertEquals("$20", listing.getPrice());
    }

    @Test
    public void testCopies() {
        listing.setCopies("3");
        assertEquals("3", listing.getCopies());
    }

    @Test
    public void testTitle() {
        listing.setTitle("Book");
        assertEquals("Book", listing.getTitle());
    }

    @Test
    public void testAuthorCreation() {
        assertNotNull(listing);
    }
}
