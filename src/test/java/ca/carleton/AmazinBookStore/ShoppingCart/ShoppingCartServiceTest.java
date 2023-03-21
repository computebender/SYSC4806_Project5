package ca.carleton.AmazinBookStore.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreRepository;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreService;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import ca.carleton.AmazinBookStore.Listing.Listing;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AmazinBookStoreApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ShoppingCartServiceTest {
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private BookstoreRepository bookstoreRepository;
    private BookstoreService bookstoreService;

    @BeforeEach
    void setUp() {
        this.shoppingCartService = new ShoppingCartService(cartRepository, itemRepository);
        this.bookstoreService = new BookstoreService(bookstoreRepository,listingRepository);
    }

    @Test
    void testCreateShoppingCart() {
        // Create ShoppingCart Object
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId("user");

        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCart);

        assertNotNull(createdShoppingCart.getId());
        assertEquals("user", createdShoppingCart.getUserId());
    }

    @Test
    void testCreateShoppingCartWithDuplicateUserId() {
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUserId("user");
        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setUserId("user");
        shoppingCartService.createShoppingCart(shoppingCart1);

        assertThrows(DuplicateKeyException.class, () -> shoppingCartService.createShoppingCart(shoppingCart2));
    }

    @Test
    void testFindAll() {
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUserId("user1");
        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setUserId("user2");
        shoppingCartService.createShoppingCart(shoppingCart1);
        shoppingCartService.createShoppingCart(shoppingCart2);

        List<ShoppingCart> foundShoppingCarts = shoppingCartService.findAll();

        assertEquals(2, foundShoppingCarts.size());
    }

    @Test
    void testFindShoppingCartByUserId() {
        // Given
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId("user1");
        shoppingCartService.createShoppingCart(shoppingCart);

        // Find ShoppingCart by ID
        ShoppingCart foundShoppingCart = shoppingCartService.findShoppingCartByUserId("user1");

        assertNotNull(foundShoppingCart.getId());
        assertEquals("user1", foundShoppingCart.getUserId());
    }

    @Test
    void testFindShoppingCartByNonExistentUserId() {
        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.findShoppingCartByUserId("user1"));
    }

    @Test
    @Transactional
    void testAddShoppingCartItem() {
        //Create bookstore
        Bookstore bookstore = new Bookstore();
        Long bookstoreId = 1L;
        bookstore.setbookstoreName("store");
        bookstore.setId(1L);
        bookstore.setListings(new ArrayList<Listing>());
        Bookstore savedBookstore = this.bookstoreService.createBookstore(bookstore);
        // Create listing
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setCopies("2");
        listing.setPrice("10.0");
        listing.setTitle("Book Title");

        bookstore = this.bookstoreService.createListing(bookstoreId, listing);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId("user1");
        shoppingCart.setItems(new ArrayList<CartItem>());
        this.shoppingCartService.createShoppingCart(shoppingCart);

        // Add listing to shopping cart
        ShoppingCart updatedShoppingCart = this.shoppingCartService.addShoppingCartItem(listing, "user1");

        assertNotNull(updatedShoppingCart.getId());
        assertEquals("user1", updatedShoppingCart.getUserId());
        assertEquals(1, updatedShoppingCart.getItems().size());
        CartItem addedCartItem = updatedShoppingCart.getItems().get(0);
        assertNotNull(addedCartItem.getId());
        assertEquals(listing.getId(), addedCartItem.getBookListingById());
        assertEquals(Integer.parseInt(listing.getCopies()), addedCartItem.getQuantity());
        assertEquals(Double.parseDouble(listing.getPrice()) * addedCartItem.getQuantity(), addedCartItem.getPrice());

    }
    @Test
    @Transactional
    public void testFindShoppingCartByUserIdNotFound() {
        String userId = "nonExistentUserId";
        Optional<ShoppingCart> shoppingCart = cartRepository.findByUserId(userId);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartService.findShoppingCartByUserId(userId);
        });
        String expectedMessage = "ShoppingCart with user ID " + userId + " not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Transactional
    public void testRemoveShoppingCartItem() {
        Long cartItemId = 1L;
        String userId = "testUser";
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItems(new ArrayList<CartItem>());
        shoppingCart.setUserId(userId);
        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCart);

        ShoppingCart savedShoppingCart = shoppingCartService.findShoppingCartByUserId(userId);

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);
        savedShoppingCart.addItem(cartItem);
        ShoppingCart result = shoppingCartService.removeShoppingCartItem(cartItemId, userId);
        assertTrue(result.getItems().isEmpty());
    }
}