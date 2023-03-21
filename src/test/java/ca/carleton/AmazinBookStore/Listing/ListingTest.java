package ca.carleton.AmazinBookStore.Listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
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
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        listing.setLocation(bookstore);
        assertEquals(bookstore, listing.getLocation());
    }

    @Test
    public void testPrice() {
        listing.setPrice(20d);
        assertEquals(20d, listing.getPrice());
    }

    @Test
    public void testCopies() {
        listing.setCopies(3);
        assertEquals(3, listing.getCopies());
    }

    @Test
    public void testTitle() {
        Book book = new Book();
        listing.setBook(book);
        assertEquals(book, listing.getBook());
    }

    @Test
    public void testAuthorCreation() {
        assertNotNull(listing);
    }
}
