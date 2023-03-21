package ca.carleton.AmazinBookStore.ShoppingCart;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Listing.Listing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class CartItemTest {
    @Test
    void testCartItem() {
        Listing bookListing = new Listing();
        bookListing.setTitle("Title");
        bookListing.setCopies("2");
        bookListing.setPrice("9.99");
        bookListing.setLocation("Ottawa");

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
