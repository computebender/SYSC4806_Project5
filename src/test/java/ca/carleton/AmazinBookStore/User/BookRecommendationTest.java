package ca.carleton.AmazinBookStore.User;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import ca.carleton.AmazinBookStore.User.BookRecommendation.BookRecommendation;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRecommendationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private HttpHeaders headers;

    private String baseUrl;
    private String bookUrl;
    private String authorUrl;
    private String publisherUrl;
    private String genreUrl;
    private String bookstoreUrl;
    private String listingUrl;
    private String userUrl;

    private User testUser1;
    private User testUser2;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        baseUrl = "http://localhost:" + port + "/api/users";
        bookUrl = "http://localhost:" + port + "/api/books";
        authorUrl = "http://localhost:" + port + "/api/authors";
        publisherUrl = "http://localhost:" + port + "/api/publishers";
        genreUrl = "http://localhost:" + port + "/api/genres";
        bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        listingUrl = "http://localhost:" + port + "/api/listings";
        userUrl = "http://localhost:" + port + "/api/users";

        testUser1 = new User();
        testUser1.setFirstName("John");
        testUser1.setLastName("Doe");
        testUser1.setEmail("johndoe@example.com");
        testUser1.setPassword("password");


        testUser2 = new User();
        testUser2.setFirstName("Thomas");
        testUser2.setLastName("Dunnigan");
        testUser2.setEmail("thomasdunnigan@example.com");
        testUser2.setPassword("password");

        createBooks();

        TestObjectMapper objectMapper = new TestObjectMapper();
        try {
            String newUser1Json = objectMapper.writeValueAsString(testUser1);
            HttpEntity<String> requestUser1 = new HttpEntity<>(newUser1Json, headers);
            ResponseEntity<User> responseUser1 = restTemplate.exchange(
                    userUrl, HttpMethod.POST, requestUser1, User.class);
            testUser1 = responseUser1.getBody();
        } catch (JsonProcessingException e) {
            System.out.println("Failed to serialize user object: " + e.getMessage());
        }

        try {
            String newUser2Json = objectMapper.writeValueAsString(testUser2);
            HttpEntity<String> requestUser2 = new HttpEntity<>(newUser2Json, headers);
            ResponseEntity<User> responseUser2 = restTemplate.exchange(
                    userUrl, HttpMethod.POST, requestUser2, User.class);
            testUser2 = responseUser2.getBody();
        } catch (JsonProcessingException e) {
            System.out.println("Failed to serialize user object: " + e.getMessage());
        }

    }

    @Test
    public void testGetRecommendationById(){
        ResponseEntity<BookRecommendation> response = restTemplate.getForEntity(
                baseUrl + "/" + testUser1.getId() + "/recommendations", BookRecommendation.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BookRecommendation bookRecommendation = response.getBody();
        assertNotNull(bookRecommendation);
        assertEquals(bookRecommendation.getRecommendations().size(), 1);
        System.out.println(bookRecommendation.getRecommendations().get(0).getTitle());
    }

    private void createBooks(){
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
        Book book1 = new Book();
        book1.setAuthor(savedAuthor1);
        book1.setPublisher(savedPublisher1);
        book1.setGenres(genres);
        book1.setDescription("This is the Description");
        book1.setIsbn("123556");
        book1.setPicture("picture/url");
        book1.setTitle("Lord of the Rings");
        book1.setDescription("Adventure story of a hobbit");
        HttpEntity<Book> request1 = new HttpEntity<>(book1);
        ResponseEntity<Book> response1 = restTemplate.postForEntity(bookUrl, request1, Book.class);
        Book savedBook1 = response1.getBody();

        Book book2 = new Book();
        book2.setAuthor(savedAuthor1);
        book2.setPublisher(savedPublisher1);
        book2.setGenres(genres);
        book2.setDescription("This is the Description");
        book2.setIsbn("12355");
        book2.setPicture("picture/url");
        book2.setTitle("Lord of the Flies");
        book2.setDescription("Adventure story of boys");
        HttpEntity<Book> request2 = new HttpEntity<>(book2);
        ResponseEntity<Book> response2 = restTemplate.postForEntity(bookUrl, request2, Book.class);
        Book savedBook2 = response2.getBody();

        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        HttpEntity<Bookstore> requestbookstore1 = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> responsebookstore1 = restTemplate.postForEntity(bookstoreUrl, requestbookstore1, Bookstore.class);
        Bookstore savedBookstore1 = responsebookstore1.getBody();


        Listing listing1 = new Listing(savedBookstore1, 25d, 3, savedBook1);
        HttpEntity<Listing> requestListing1 = new HttpEntity<>(listing1);
        ResponseEntity<Listing> responseListing1 = restTemplate.postForEntity(listingUrl, requestListing1, Listing.class);
        Listing savedListing1 = responseListing1.getBody();

        Listing listing2 = new Listing(savedBookstore1, 25d, 3, savedBook1);
        HttpEntity<Listing> requestListing2 = new HttpEntity<>(listing2);
        ResponseEntity<Listing> responseListing2 = restTemplate.postForEntity(listingUrl, requestListing2, Listing.class);
        Listing savedListing2 = responseListing2.getBody();

        Listing listing3 = new Listing(savedBookstore1, 25d, 3, savedBook2);
        HttpEntity<Listing> requestListing3 = new HttpEntity<>(listing3);
        ResponseEntity<Listing> responseListing3 = restTemplate.postForEntity(listingUrl, requestListing3, Listing.class);
        Listing savedListing3 = responseListing3.getBody();

        List<Listing> purchaseHistory1 = new ArrayList<>();
        purchaseHistory1.add(savedListing1);


        testUser1.setPurchaseHistory(purchaseHistory1);

        List<Listing> purchaseHistory2 = new ArrayList<>();
        purchaseHistory2.add(savedListing2);
        purchaseHistory2.add(savedListing3);

        testUser2.setPurchaseHistory(purchaseHistory2);

    }

    public class TestObjectMapper extends ObjectMapper {
        public TestObjectMapper() {
            super();
            this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
            this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            this.addMixIn(User.class, UserControllerTest.TestObjectMapper.UserMixin.class);
        }

        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
        abstract class UserMixin {
            @JsonProperty
            abstract String getPassword();
        }
    }

}
