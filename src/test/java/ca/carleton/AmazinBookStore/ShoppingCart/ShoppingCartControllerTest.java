package ca.carleton.AmazinBookStore.ShoppingCart;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ShoppingCartControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/carts";
    }

    @Test
    public void createShoppingCart(){
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
    }

    @Test
    public void getShoppingCartById(){
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);

        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);
        assertEquals(HttpStatus.OK ,response_cart.getStatusCode());
        assertEquals(userId ,response_cart.getBody().getUserId());
    }

    @Test
    public void getAllShoppingCarts(){
        // Create 3 shopping carts
        String userId1 = "user1";
        String userId2 = "user2";
        String userId3 = "user3";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId1, request1, ShoppingCart.class);
        ResponseEntity<ShoppingCart> response2 = restTemplate.postForEntity(baseUrl + "/" +userId2, request1, ShoppingCart.class);
        ResponseEntity<ShoppingCart> response3 = restTemplate.postForEntity(baseUrl + "/" +userId3, request1, ShoppingCart.class);

        ResponseEntity<List<ShoppingCart>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ShoppingCart>>() {
                });
        List<ShoppingCart> shoppingCarts = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, shoppingCarts.size());
    }
    @Test
    public void addItem(){
//Create Author
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        String authUrl = "http://localhost:" + port + "/api/authors";
        HttpEntity<Author> request_author = new HttpEntity<>(author);
        ResponseEntity<Author> response_author = restTemplate.postForEntity(authUrl, request_author, Author.class);
        Author savedAuthor = response_author.getBody();
        assertEquals(HttpStatus.CREATED, response_author.getStatusCode());
        assertEquals("First", savedAuthor.getFirstName());
        assertEquals("Last", savedAuthor.getLastName());
        //Create Publisher
        Publisher publisher = new Publisher();
        publisher.setName("Company");
        publisher.setId(1L);
        String pubUrl = "http://localhost:" + port + "/api/publishers";
        HttpEntity<Publisher> request_pub = new HttpEntity<>(publisher);
        ResponseEntity<Publisher> response_pub = restTemplate.postForEntity(pubUrl, request_pub, Publisher.class);
        Publisher savedPublisher = response_pub.getBody();
        assertEquals(HttpStatus.CREATED, response_pub.getStatusCode());
        assertEquals("Company", savedPublisher.getName());

        //Create Genre
        Genre genre = new Genre();
        genre.setName("Horror");
        HttpEntity<Genre> request_genre = new HttpEntity<>(genre);
        String genreUrl = "http://localhost:" + port + "/api/genres";
        ResponseEntity<Genre> response_genre = restTemplate.postForEntity(genreUrl, request_genre, Genre.class);
        Genre savedGenre = response_genre.getBody();
        assertEquals(HttpStatus.CREATED, response_genre.getStatusCode());
        assertEquals("Horror", savedGenre.getName());
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
        HttpEntity<Book> request_book1 = new HttpEntity<>(book);
        String bookUrl = "http://localhost:" + port + "/api/books";
        ResponseEntity<Book> response_book1 = restTemplate.postForEntity(bookUrl, request_book1, Book.class);
        Book savedBook1 = response_book1.getBody();

        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());

        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);

        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setBook(savedBook1);
        listing.setCopies(10);
        listing.setPrice(20.00);
        listing.setLocation(savedBookstore);

        String listingUrl = "http://localhost:" + port + "/api/listings";
        HttpEntity<Listing> request2 = new HttpEntity<>(listing);

        ResponseEntity<Listing> response2 = restTemplate.postForEntity(listingUrl, request2, Listing.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(listingUrl + "/" + response2.getBody().getId(), Listing.class);
        Listing listing_1 = response3.getBody();

        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing_1);

        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getBook().getIsbn(), listing.getBook().getIsbn());
        //Get shopping cart
        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);

        assertEquals(response_cart.getBody().getItems().size(), 1);
    }

    @Test
    public void removeItem(){
        //Create Author
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        String authUrl = "http://localhost:" + port + "/api/authors";
        HttpEntity<Author> request_author = new HttpEntity<>(author);
        ResponseEntity<Author> response_author = restTemplate.postForEntity(authUrl, request_author, Author.class);
        Author savedAuthor = response_author.getBody();
        assertEquals(HttpStatus.CREATED, response_author.getStatusCode());
        assertEquals("First", savedAuthor.getFirstName());
        assertEquals("Last", savedAuthor.getLastName());
        //Create Publisher
        Publisher publisher = new Publisher();
        publisher.setName("Company");
        publisher.setId(1L);
        String pubUrl = "http://localhost:" + port + "/api/publishers";
        HttpEntity<Publisher> request_pub = new HttpEntity<>(publisher);
        ResponseEntity<Publisher> response_pub = restTemplate.postForEntity(pubUrl, request_pub, Publisher.class);
        Publisher savedPublisher = response_pub.getBody();
        assertEquals(HttpStatus.CREATED, response_pub.getStatusCode());
        assertEquals("Company", savedPublisher.getName());

        //Create Genre
        Genre genre = new Genre();
        genre.setName("Horror");
        HttpEntity<Genre> request_genre = new HttpEntity<>(genre);
        String genreUrl = "http://localhost:" + port + "/api/genres";
        ResponseEntity<Genre> response_genre = restTemplate.postForEntity(genreUrl, request_genre, Genre.class);
        Genre savedGenre = response_genre.getBody();
        assertEquals(HttpStatus.CREATED, response_genre.getStatusCode());
        assertEquals("Horror", savedGenre.getName());
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
        HttpEntity<Book> request_book1 = new HttpEntity<>(book);
        String bookUrl = "http://localhost:" + port + "/api/books";
        ResponseEntity<Book> response_book1 = restTemplate.postForEntity(bookUrl, request_book1, Book.class);
        Book savedBook1 = response_book1.getBody();

        Book book2 = new Book();
        book2.setIsbn(54321);
        book2.setDescription("Description2");
        book2.setPicture("Picture2");
        book2.setAuthor(savedAuthor);
        book2.setPublisher(savedPublisher);
        book2.setGenres(genres);
        book2.setTitle("Title2");
        HttpEntity<Book> request_book2 = new HttpEntity<>(book2);
        ResponseEntity<Book> response_book2 = restTemplate.postForEntity(bookUrl, request_book2, Book.class);
        Book savedBook2 = response_book2.getBody();

        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());

        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);

        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setBook(savedBook1);
        listing.setCopies(10);
        listing.setPrice(20.00);
        listing.setLocation(savedBookstore);

        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setBook(savedBook2);
        listing2.setCopies(5);
        listing2.setPrice(25.00);
        listing2.setLocation(savedBookstore);
        String listingUrl = "http://localhost:" + port + "/api/listings";
        HttpEntity<Listing> request2 = new HttpEntity<>(listing);
        HttpEntity<Listing> request5 = new HttpEntity<>(listing2);

        ResponseEntity<Listing> response2 = restTemplate.postForEntity(listingUrl, request2, Listing.class);
        ResponseEntity<Listing> response5 = restTemplate.postForEntity(listingUrl, request5, Listing.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(listingUrl + "/" + response2.getBody().getId(), Listing.class);
        Listing listing_1 = response3.getBody();
        ResponseEntity<Listing> response7 = restTemplate.getForEntity(listingUrl + "/" + response5.getBody().getId(), Listing.class);
        Listing listing_2 = response7.getBody();

        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing_1);
        HttpEntity<Listing> request6 = new HttpEntity<>(listing_2);

        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getBook().getIsbn(), listing.getBook().getIsbn());
        ResponseEntity<ShoppingCart> response6 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request6, ShoppingCart.class);
        assertEquals(response6.getBody().getItems().get(1).getBookListing().getBook().getIsbn(), listing2.getBook().getIsbn());
        //Get shopping cart
        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);

        //Remove item from shopping cart
        assertEquals(response_cart.getBody().getItems().size(), 2);
        CartItem item1 = response6.getBody().getItems().get(0);
        item1.setId(1L);
        CartItem item2 = response4.getBody().getItems().get(0);
        item1.setId(2L);
        HttpEntity<Long> request7 = new HttpEntity<>(item1.getId());
        HttpEntity<Long> request8 = new HttpEntity<>(item2.getId());

        //Remove CartItem 1
        ResponseEntity resp = restTemplate.exchange(baseUrl + "/" + userId +"/remove-item", HttpMethod.DELETE, request7, Void.class);
        response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);
        assertEquals(1,response_cart.getBody().getItems().size());
        //Remove CartItem 2
        ResponseEntity resp2 = restTemplate.exchange(baseUrl + "/" + userId +"/remove-item", HttpMethod.DELETE, request8, Void.class);
        response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);
        assertEquals( 0,response_cart.getBody().getItems().size());
    }

    @Test
    @Transactional
    public void clearCart(){
        //Create Author
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        String authUrl = "http://localhost:" + port + "/api/authors";
        HttpEntity<Author> request_author = new HttpEntity<>(author);
        ResponseEntity<Author> response_author = restTemplate.postForEntity(authUrl, request_author, Author.class);
        Author savedAuthor = response_author.getBody();
        assertEquals(HttpStatus.CREATED, response_author.getStatusCode());
        assertEquals("First", savedAuthor.getFirstName());
        assertEquals("Last", savedAuthor.getLastName());

        //Create Publisher
        Publisher publisher = new Publisher();
        publisher.setName("Company");
        publisher.setId(1L);
        String pubUrl = "http://localhost:" + port + "/api/publishers";
        HttpEntity<Publisher> request_pub = new HttpEntity<>(publisher);
        ResponseEntity<Publisher> response_pub = restTemplate.postForEntity(pubUrl, request_pub, Publisher.class);
        Publisher savedPublisher = response_pub.getBody();
        assertEquals(HttpStatus.CREATED, response_pub.getStatusCode());
        assertEquals("Company", savedPublisher.getName());

        //Create Genre
        Genre genre = new Genre();
        genre.setName("Horror");
        HttpEntity<Genre> request_genre = new HttpEntity<>(genre);
        String genreUrl = "http://localhost:" + port + "/api/genres";
        ResponseEntity<Genre> response_genre = restTemplate.postForEntity(genreUrl, request_genre, Genre.class);
        Genre savedGenre = response_genre.getBody();
        assertEquals(HttpStatus.CREATED, response_genre.getStatusCode());
        assertEquals("Horror", savedGenre.getName());
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
        HttpEntity<Book> request_book1 = new HttpEntity<>(book);
        String bookUrl = "http://localhost:" + port + "/api/books";
        ResponseEntity<Book> response_book1 = restTemplate.postForEntity(bookUrl, request_book1, Book.class);
        Book savedBook1 = response_book1.getBody();

        Book book2 = new Book();
        book2.setIsbn(54321);
        book2.setDescription("Description2");
        book2.setPicture("Picture2");
        book2.setAuthor(savedAuthor);
        book2.setPublisher(savedPublisher);
        book2.setGenres(genres);
        book2.setTitle("Title2");
        HttpEntity<Book> request_book2 = new HttpEntity<>(book2);
        ResponseEntity<Book> response_book2 = restTemplate.postForEntity(bookUrl, request_book2, Book.class);
        Book savedBook2 = response_book2.getBody();

        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());

        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setBook(savedBook1);
        listing.setCopies(10);
        listing.setPrice(20.00);
        listing.setLocation(savedBookstore);

        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setBook(savedBook2);
        listing2.setCopies(5);
        listing2.setPrice(25.00);
        listing2.setLocation(savedBookstore);
        String listingUrl = "http://localhost:" + port + "/api/listings";
        HttpEntity<Listing> request2 = new HttpEntity<>(listing);
        HttpEntity<Listing> request5 = new HttpEntity<>(listing2);

        ResponseEntity<Listing> response2 = restTemplate.postForEntity(listingUrl, request2, Listing.class);
        ResponseEntity<Listing> response5 = restTemplate.postForEntity(listingUrl, request5, Listing.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(listingUrl + "/" + response2.getBody().getId(), Listing.class);
        Listing listing_1 = response3.getBody();
        ResponseEntity<Listing> response7 = restTemplate.getForEntity(listingUrl + "/" + response5.getBody().getId(), Listing.class);
        Listing listing_2 = response7.getBody();

        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing_1);
        HttpEntity<Listing> request6 = new HttpEntity<>(listing_2);

        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getBook().getIsbn(), listing.getBook().getIsbn());
        ResponseEntity<ShoppingCart> response6 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request6, ShoppingCart.class);
        assertEquals(response6.getBody().getItems().get(1).getBookListing().getBook().getIsbn(), listing2.getBook().getIsbn());
        //Get shopping cart
        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);
        assertEquals(response_cart.getBody().getItems().size(), 2);
        ResponseEntity<ShoppingCart> response9 = restTemplate.postForEntity(baseUrl + "/" + userId +"/clear", new HttpEntity<String>(""), ShoppingCart.class);
        assertEquals(HttpStatus.CREATED,response9.getStatusCode());
        assertEquals(0,response9.getBody().getItems().size());
    }

    @Test
    @Transactional
    public void checkoutCart(){
        //Create Author
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        String authUrl = "http://localhost:" + port + "/api/authors";
        HttpEntity<Author> request_author = new HttpEntity<>(author);
        ResponseEntity<Author> response_author = restTemplate.postForEntity(authUrl, request_author, Author.class);
        Author savedAuthor = response_author.getBody();
        assertEquals(HttpStatus.CREATED, response_author.getStatusCode());
        assertEquals("First", savedAuthor.getFirstName());
        assertEquals("Last", savedAuthor.getLastName());

        //Create Publisher
        Publisher publisher = new Publisher();
        publisher.setName("Company");
        publisher.setId(1L);
        String pubUrl = "http://localhost:" + port + "/api/publishers";
        HttpEntity<Publisher> request_pub = new HttpEntity<>(publisher);
        ResponseEntity<Publisher> response_pub = restTemplate.postForEntity(pubUrl, request_pub, Publisher.class);
        Publisher savedPublisher = response_pub.getBody();
        assertEquals(HttpStatus.CREATED, response_pub.getStatusCode());
        assertEquals("Company", savedPublisher.getName());

        //Create Genre
        Genre genre = new Genre();
        genre.setName("Horror");
        HttpEntity<Genre> request_genre = new HttpEntity<>(genre);
        String genreUrl = "http://localhost:" + port + "/api/genres";
        ResponseEntity<Genre> response_genre = restTemplate.postForEntity(genreUrl, request_genre, Genre.class);
        Genre savedGenre = response_genre.getBody();
        assertEquals(HttpStatus.CREATED, response_genre.getStatusCode());
        assertEquals("Horror", savedGenre.getName());
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
        HttpEntity<Book> request_book1 = new HttpEntity<>(book);
        String bookUrl = "http://localhost:" + port + "/api/books";
        ResponseEntity<Book> response_book1 = restTemplate.postForEntity(bookUrl, request_book1, Book.class);
        Book savedBook1 = response_book1.getBody();

        Book book2 = new Book();
        book2.setIsbn(54321);
        book2.setDescription("Description2");
        book2.setPicture("Picture2");
        book2.setAuthor(savedAuthor);
        book2.setPublisher(savedPublisher);
        book2.setGenres(genres);
        book2.setTitle("Title2");
        HttpEntity<Book> request_book2 = new HttpEntity<>(book2);
        ResponseEntity<Book> response_book2 = restTemplate.postForEntity(bookUrl, request_book2, Book.class);
        Book savedBook2 = response_book2.getBody();

        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());

        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);

        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setBook(savedBook1);
        listing.setCopies(10);
        listing.setPrice(20.00);
        listing.setLocation(savedBookstore);

        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setBook(savedBook2);
        listing2.setCopies(5);
        listing2.setPrice(25.00);
        listing2.setLocation(savedBookstore);
        String listingUrl = "http://localhost:" + port + "/api/listings";
        HttpEntity<Listing> request2 = new HttpEntity<>(listing);
        HttpEntity<Listing> request5 = new HttpEntity<>(listing2);

        ResponseEntity<Listing> response2 = restTemplate.postForEntity(listingUrl, request2, Listing.class);
        ResponseEntity<Listing> response5 = restTemplate.postForEntity(listingUrl, request5, Listing.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(listingUrl + "/" + response2.getBody().getId(), Listing.class);
        Listing listing_1 = response3.getBody();
        ResponseEntity<Listing> response7 = restTemplate.getForEntity(listingUrl + "/" + response5.getBody().getId(), Listing.class);
        Listing listing_2 = response7.getBody();

        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing_1);
        HttpEntity<Listing> request6 = new HttpEntity<>(listing_2);

        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getBook().getIsbn(), listing.getBook().getIsbn());
        ResponseEntity<ShoppingCart> response6 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request6, ShoppingCart.class);
        assertEquals(response6.getBody().getItems().get(1).getBookListing().getBook().getIsbn(), listing2.getBook().getIsbn());
        //Get shopping cart
        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);

        assertEquals(response_cart.getBody().getItems().size(), 2);
        //checkout shopping cart
        ResponseEntity<Double> response9 = restTemplate.postForEntity(baseUrl + "/" + userId +"/checkout", new HttpEntity<String>(""), Double.class);
        assertEquals(HttpStatus.CREATED,response9.getStatusCode());
        assertEquals(325,response9.getBody());
    }

}
