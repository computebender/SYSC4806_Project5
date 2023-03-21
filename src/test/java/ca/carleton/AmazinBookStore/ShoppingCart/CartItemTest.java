package ca.carleton.AmazinBookStore.ShoppingCart;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class CartItemTest {
    @Test
    void testCartItem() {
        Bookstore bookstore = new Bookstore();
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        Publisher publisher = new Publisher();
        Book book = new Book();
        book.setIsbn(12345);
        book.setDescription("Description");
        book.setPicture("Picture");
        book.setAuthor(author);
        Listing bookListing = new Listing();
        bookListing.setBook(book);
        bookListing.setCopies(2);
        bookListing.setPrice(9.99);
        bookListing.setLocation(bookstore);

        CartItem cartItem = new CartItem(bookListing);

        assertEquals(bookListing, cartItem.getBookListing());
        assertEquals(2,  cartItem.getQuantity());
        assertEquals(19.98, cartItem.getPrice());

        cartItem.setQuantity(10);
        assertEquals(199.8, cartItem.getPrice());

        cartItem.setPrice(80.0);
        assertEquals(800.0, cartItem.getPrice());
    }
}
