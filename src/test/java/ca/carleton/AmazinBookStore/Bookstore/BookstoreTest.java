package ca.carleton.AmazinBookStore.Bookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;
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
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        Book book = new Book();
        Listing listing = new Listing(bookstore, 15d, 1, book);

        bookstore.addListing(listing);
        List<Listing> listings = new ArrayList<Listing>();
        listings.add(listing);
        assertEquals(listings, bookstore.getListings());
    }

    @Test
    public void testremoveListing() {
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        Book book1 = new Book();
        Listing listing = new Listing(bookstore, 15d, 1, book1);
        listing.setId(1L);

        Book book2 = new Book();
        Listing listing2 = new Listing(bookstore, 20d, 1, book2);
        listing2.setId(2L);


        bookstore.addListing(listing);
        bookstore.addListing(listing2);
        bookstore.removeListing(1L);
        List<Listing> listings = new ArrayList<Listing>();
        listings.add(listing2);
        assertEquals(listings, bookstore.getListings());
    }
}
