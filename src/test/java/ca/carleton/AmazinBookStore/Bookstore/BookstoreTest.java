package ca.carleton.AmazinBookStore.Bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Listing.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BookstoreTest {
    private Bookstore bookstore;

    @BeforeEach
    public void setUp() {
        bookstore = new Bookstore();
    }

    @Test
    public void testId() {
        bookstore.setId(1L);
        assertEquals(1L, bookstore.getId().longValue());
    }

    @Test
    public void testbookstoreName() {
        bookstore.setbookstoreName("Bookstore 1");
        assertEquals("Bookstore 1", bookstore.getbookstoreName());
    }

    @Test
    public void testBookstoreCreation() {
        assertNotNull(bookstore);
    }

    @Test
    public void testaddListing() {
        Listing listing = new Listing("1", "$15", "1", "Book 1");

        bookstore.addListing(listing);
        List<Listing> listings = new ArrayList<Listing>();
        listings.add(listing);
        assertEquals(listings, bookstore.getListings());
    }

    @Test
    public void testremoveListing() {
        Listing listing = new Listing("1", "$15", "1", "Book 1");
        listing.setId(1L);

        Listing listing2 = new Listing("1", "$20", "1", "Book 2");
        listing2.setId(2L);


        bookstore.addListing(listing);
        bookstore.addListing(listing2);
        bookstore.removeListing(1L);
        List<Listing> listings = new ArrayList<Listing>();
        listings.add(listing2);
        assertEquals(listings, bookstore.getListings());
    }
}
