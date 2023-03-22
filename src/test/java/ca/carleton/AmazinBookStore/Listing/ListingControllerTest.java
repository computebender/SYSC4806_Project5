package ca.carleton.AmazinBookStore.Listing;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ListingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;
    private String bookUrl;
    private String bookstoreUrl;
    private String authorUrl;
    private String publisherUrl;
    private String genreUrl;


    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/listings";
        bookUrl = "http://localhost:" + port + "/api/books";
        bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        authorUrl = "http://localhost:" + port + "/api/authors";
        publisherUrl = "http://localhost:" + port + "/api/publishers";
        genreUrl = "http://localhost:" + port + "/api/genres";;
    }

    @Test
    public void testGetAllListings() {
        Book savedBook = createBook();

        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        HttpEntity<Bookstore> requestbookstore1 = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> responsebookstore1 = restTemplate.postForEntity(bookstoreUrl, requestbookstore1, Bookstore.class);
        Bookstore savedBookstore1 = responsebookstore1.getBody();

        Book book1 = new Book();
        HttpEntity<Book> requestbook1 = new HttpEntity<>(book1);
        ResponseEntity<Book> responsebook1 = restTemplate.postForEntity(baseUrl, requestbook1, Book.class);
        Book savedBook1 = responsebook1.getBody();

        Listing listing1 = new Listing(savedBookstore1, 25d, 3, savedBook);
        Listing listing2 = new Listing(savedBookstore1, 30d, 2, savedBook);

        HttpEntity<Listing> request1 = new HttpEntity<>(listing1);
        ResponseEntity<Listing> response1 = restTemplate.postForEntity(baseUrl, request1, Listing.class);
        Listing savedListing1 = response1.getBody();

        HttpEntity<Listing> request2 = new HttpEntity<>(listing2);
        ResponseEntity<Listing> response2 = restTemplate.postForEntity(baseUrl, request2, Listing.class);
        Listing savedListing2 = response2.getBody();

        ResponseEntity<List<Listing>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Listing>>() {
                });
        List<Listing> listings = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, listings.size());
    }

    @Test
    public void testCreateListing() {
        Book savedBook = createBook();

        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        HttpEntity<Bookstore> requestbookstore1 = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> responsebookstore1 = restTemplate.postForEntity(bookstoreUrl, requestbookstore1, Bookstore.class);
        Bookstore savedBookstore1 = responsebookstore1.getBody();


        Listing listing = new Listing(savedBookstore1, 25d, 3, savedBook);
        HttpEntity<Listing> request = new HttpEntity<>(listing);
        ResponseEntity<Listing> response = restTemplate.postForEntity(baseUrl, request, Listing.class);
        Listing savedListing = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedBookstore1.getId(), savedListing.getLocation().getId());
    }

    @Test
    public void testGetListingById() {
        Book savedBook = createBook();

        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        HttpEntity<Bookstore> requestbookstore1 = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> responsebookstore1 = restTemplate.postForEntity(bookstoreUrl, requestbookstore1, Bookstore.class);
        Bookstore savedBookstore1 = responsebookstore1.getBody();

        Listing listing = new Listing(savedBookstore1, 25d, 3, savedBook);
        listing.setId(1L);
        HttpEntity<Listing> request1 = new HttpEntity<>(listing);
        ResponseEntity<Listing> response1 = restTemplate.postForEntity(baseUrl, request1, Listing.class);
        Listing savedListing1 = response1.getBody();
        ResponseEntity<Listing> response = restTemplate.getForEntity(baseUrl + "/" + listing.getId(), Listing.class);
        Listing listingResponse = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listingResponse.getId(), savedListing1.getId());
    }

    @Test
    public void testUpdateListingById() {
        Book savedBook = createBook();

        Bookstore bookstore1 = new Bookstore();
        bookstore1.setbookstoreName("John's Bookstore");
        HttpEntity<Bookstore> requestbookstore1 = new HttpEntity<>(bookstore1);
        ResponseEntity<Bookstore> responsebookstore1 = restTemplate.postForEntity(bookstoreUrl, requestbookstore1, Bookstore.class);
        Bookstore savedBookstore1 = responsebookstore1.getBody();

        Bookstore bookstore2 = new Bookstore();
        bookstore2.setbookstoreName("Jenny's Bookstore");
        HttpEntity<Bookstore> requestbookstore2 = new HttpEntity<>(bookstore2);
        ResponseEntity<Bookstore> responsebookstore2 = restTemplate.postForEntity(bookstoreUrl, requestbookstore2, Bookstore.class);
        Bookstore savedBookstore2 = responsebookstore2.getBody();

        Listing listing1 = new Listing(savedBookstore1, 25d, 3, savedBook);
        listing1.setId(1L);

        HttpEntity<Listing> request1 = new HttpEntity<>(listing1);
        ResponseEntity<Listing> response1 = restTemplate.postForEntity(baseUrl, request1, Listing.class);
        Listing savedListing1 = response1.getBody();

        Listing partialListing = new Listing(savedBookstore2, 25d, 3, savedBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Listing> request = new HttpEntity<>(partialListing, headers);
        ResponseEntity<Listing> response = restTemplate.exchange(
                baseUrl + "/" + listing1.getId(),
                HttpMethod.PUT,
                request,
                Listing.class);
        Listing updatedListing = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedBookstore2.getId(), updatedListing.getLocation().getId());
    }

    @Test
    public void testDeleteListing() {
        long listingId = 2;
        restTemplate.delete(baseUrl + "/" + listingId);
        ResponseEntity<Listing> response = restTemplate.getForEntity(baseUrl + "/" + listingId, Listing.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    public Book createBook() {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        HttpEntity<Author> requestauthor1 = new HttpEntity<>(author1);
        ResponseEntity<Author> responseauthor1 = restTemplate.postForEntity(authorUrl, requestauthor1, Author.class);
        Author savedAuthor1 = responseauthor1.getBody();

        Publisher publisher1 = new Publisher();
        publisher1.setName("First2");

        HttpEntity<Publisher> requestpublisher1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> responsepublisher1 = restTemplate.postForEntity(publisherUrl, requestpublisher1, Publisher.class);
        Publisher savedPublisher1 = responsepublisher1.getBody();

        Genre genre1 = new Genre();
        genre1.setName("Fiction");
        HttpEntity<Genre> requestgenre1 = new HttpEntity<>(genre1);
        ResponseEntity<Genre> responsegenre1 = restTemplate.postForEntity(genreUrl, requestgenre1, Genre.class);
        Genre savedGenre1 = responsegenre1.getBody();

        Genre genre2 = new Genre();
        genre1.setName("Non-Fiction");
        HttpEntity<Genre> requestgenre2 = new HttpEntity<>(genre2);
        ResponseEntity<Genre> responsegenre2 = restTemplate.postForEntity(genreUrl, requestgenre2, Genre.class);
        Genre savedGenre2 = responsegenre2.getBody();

        List<Genre> genres = new ArrayList<>();
        genres.add(savedGenre1);
        genres.add(savedGenre2);

        // Create two Books
        Book book = new Book();
        book.setAuthor(savedAuthor1);
        book.setPublisher(savedPublisher1);
        book.setGenres(genres);
        book.setDescription("This is the Description");
        book.setIsbn("123556");
        book.setPicture("picture/url");
        book.setTitle("Lord of the Rings");
        book.setDescription("Adventure story of a hobbit");
        HttpEntity<Book> requestBook = new HttpEntity<>(book);
        ResponseEntity<Book> responseBookRequest = restTemplate.postForEntity(bookUrl, requestBook, Book.class);
        Book savedBook = responseBookRequest.getBody();
        return savedBook;
    }

}

