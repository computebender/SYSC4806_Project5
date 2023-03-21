package ca.carleton.AmazinBookStore.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Author.AuthorRepository;
import ca.carleton.AmazinBookStore.Author.AuthorService;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Book.BookRepository;
import ca.carleton.AmazinBookStore.Book.BookService;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreRepository;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreService;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Genre.GenreRepository;
import ca.carleton.AmazinBookStore.Genre.GenreService;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import ca.carleton.AmazinBookStore.Listing.ListingService;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import ca.carleton.AmazinBookStore.Publisher.PublisherRepository;
import ca.carleton.AmazinBookStore.Publisher.PublisherService;
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
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private GenreRepository genreRepository;
    private BookstoreService bookstoreService;

    private ListingService listingService;
    private BookService bookService;

    private AuthorService authorService;

    private PublisherService publisherService;

    private GenreService genreService;

    @BeforeEach
    void setUp() {
        this.shoppingCartService = new ShoppingCartService(cartRepository, itemRepository);
        this.bookstoreService = new BookstoreService(bookstoreRepository,listingRepository);
        this.listingService = new ListingService(listingRepository);
        this.bookService = new BookService(bookRepository);
        this.authorService = new AuthorService(authorRepository);
        this.publisherService = new PublisherService(publisherRepository);
        this.genreService = new GenreService(genreRepository);
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
        //Create Author
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        Author savedAuthor = this.authorService.createAuthor(author);

        //Create Publisher
        Publisher publisher = new Publisher();
        publisher.setName("Company");
        publisher.setId(1L);
        Publisher savedPublisher = this.publisherService.createPublisher(publisher);

        //Create Genre
        Genre genre = new Genre();
        genre.setName("Horror");
        Genre savedGenre = this.genreService.createGenre(genre);
        List<Genre> genres = new ArrayList<>();
        genres.add(savedGenre);

        //Create Book
        Book book = new Book();
        book.setIsbn(12345);
        book.setDescription("Description");
        book.setPicture("Picture");
        book.setAuthor(savedAuthor);
        book.setPublisher(savedPublisher);
        book.setGenres(genres);
        book.setTitle("Title");

        Book book2 = new Book();
        book2.setIsbn(12345);
        book2.setDescription("Description");
        book2.setPicture("Picture");
        book2.setAuthor(savedAuthor);
        book2.setPublisher(savedPublisher);
        book2.setGenres(genres);
        book2.setTitle("Title");

        Book savedBook = this.bookService.createBook(book);
        Book savedBook2 = this.bookService.createBook(book2);

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
        listing.setCopies(2);
        listing.setPrice(10.0);
        listing.setBook(book);
        listing.setLocation(savedBookstore);

        Listing savedListing = this.listingService.createListing(listing);

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
        assertEquals(listing.getCopies(), addedCartItem.getQuantity());
        assertEquals(listing.getPrice() * addedCartItem.getQuantity(), addedCartItem.getPrice());

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

    @Test
    @Transactional
    public void testClearShoppingCart(){
        //Create Author
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        Author savedAuthor = this.authorService.createAuthor(author);

        //Create Publisher
        Publisher publisher = new Publisher();
        publisher.setName("Company");
        publisher.setId(1L);
        Publisher savedPublisher = this.publisherService.createPublisher(publisher);

        //Create Genre
        Genre genre = new Genre();
        genre.setName("Horror");
        Genre savedGenre = this.genreService.createGenre(genre);
        List<Genre> genres = new ArrayList<>();
        genres.add(savedGenre);

        //Create Book
        Book book = new Book();
        book.setIsbn(12345);
        book.setDescription("Description");
        book.setPicture("Picture");
        book.setAuthor(savedAuthor);
        book.setPublisher(savedPublisher);
        book.setGenres(genres);
        book.setTitle("Title");

        Book book2 = new Book();
        book2.setIsbn(12345);
        book2.setDescription("Description");
        book2.setPicture("Picture");
        book2.setAuthor(savedAuthor);
        book2.setPublisher(savedPublisher);
        book2.setGenres(genres);
        book2.setTitle("Title");

        Book savedBook = this.bookService.createBook(book);
        Book savedBook2 = this.bookService.createBook(book2);

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
        listing.setCopies(2);
        listing.setPrice(10.0);
        listing.setBook(book);
        listing.setLocation(savedBookstore);

        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setId(2L);
        listing2.setCopies(3);
        listing2.setPrice(15.0);
        listing2.setBook(book2);
        listing2.setLocation(savedBookstore);

        Listing new_listing1 = this.listingService.createListing(listing);
        Listing new_listing2 = this.listingService.createListing(listing2);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId("user1");
        shoppingCart.setItems(new ArrayList<CartItem>());
        this.shoppingCartService.createShoppingCart(shoppingCart);

        // Add listing to shopping cart
        ShoppingCart updatedShoppingCart = this.shoppingCartService.addShoppingCartItem(listing, "user1");
        updatedShoppingCart = this.shoppingCartService.addShoppingCartItem(listing2, "user1");

        assertNotNull(updatedShoppingCart.getId());
        assertEquals("user1", updatedShoppingCart.getUserId());
        assertEquals(2, updatedShoppingCart.getItems().size());
        CartItem addedCartItem = updatedShoppingCart.getItems().get(0);
        assertNotNull(addedCartItem.getId());
        assertEquals(listing.getId(), addedCartItem.getBookListingById());
        assertEquals(listing.getCopies(), addedCartItem.getQuantity());
        assertEquals(listing.getPrice() * addedCartItem.getQuantity(), addedCartItem.getPrice());
        //clear shopping cart and check to see 0 items remain
        this.shoppingCartService.clearShoppingCart("user1");
        ShoppingCart shoppingCart1 = this.shoppingCartService.findShoppingCartByUserId("user1");
        assertEquals(0,shoppingCart1.getItems().size());

    }

    @Test
    @Transactional
    public void testCheckoutShoppingCart(){
        //Create Author
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        Author savedAuthor = this.authorService.createAuthor(author);

        //Create Publisher
        Publisher publisher = new Publisher();
        publisher.setName("Company");
        publisher.setId(1L);
        Publisher savedPublisher = this.publisherService.createPublisher(publisher);

        //Create Genre
        Genre genre = new Genre();
        genre.setName("Horror");
        Genre savedGenre = this.genreService.createGenre(genre);
        List<Genre> genres = new ArrayList<>();
        genres.add(savedGenre);

        //Create Book
        Book book = new Book();
        book.setIsbn(12345);
        book.setDescription("Description");
        book.setPicture("Picture");
        book.setAuthor(savedAuthor);
        book.setPublisher(savedPublisher);
        book.setGenres(genres);
        book.setTitle("Title");

        Book book2 = new Book();
        book2.setIsbn(12345);
        book2.setDescription("Description");
        book2.setPicture("Picture");
        book2.setAuthor(savedAuthor);
        book2.setPublisher(savedPublisher);
        book2.setGenres(genres);
        book2.setTitle("Title");

        Book savedBook = this.bookService.createBook(book);
        Book savedBook2 = this.bookService.createBook(book2);

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
        listing.setCopies(2);
        listing.setPrice(10.0);
        listing.setBook(book);
        listing.setLocation(savedBookstore);

        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setId(2L);
        listing2.setCopies(3);
        listing2.setPrice(15.0);
        listing2.setBook(book2);
        listing2.setLocation(savedBookstore);

        Listing newListing1 = this.listingService.createListing(listing);
        Listing newListing2 = this.listingService.createListing(listing2);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId("user1");
        shoppingCart.setItems(new ArrayList<CartItem>());
        this.shoppingCartService.createShoppingCart(shoppingCart);

        // Add listing to shopping cart
        ShoppingCart updatedShoppingCart = this.shoppingCartService.addShoppingCartItem(listing, "user1");
        updatedShoppingCart = this.shoppingCartService.addShoppingCartItem(listing2, "user1");

        assertNotNull(updatedShoppingCart.getId());
        assertEquals("user1", updatedShoppingCart.getUserId());
        assertEquals(2, updatedShoppingCart.getItems().size());
        CartItem addedCartItem = updatedShoppingCart.getItems().get(0);
        assertNotNull(addedCartItem.getId());
        assertEquals(listing.getId(), addedCartItem.getBookListingById());
        assertEquals(listing.getCopies(), addedCartItem.getQuantity());
        assertEquals(listing.getPrice() * addedCartItem.getQuantity(), addedCartItem.getPrice());
        //clear shopping cart and check to see 0 items remain
        this.shoppingCartService.checkoutShoppingCart("user1");
        ShoppingCart shoppingCart1 = this.shoppingCartService.findShoppingCartByUserId("user1");
        assertEquals(0,shoppingCart1.getItems().size());
    }

}