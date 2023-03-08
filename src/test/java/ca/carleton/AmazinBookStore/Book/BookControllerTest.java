package ca.carleton.AmazinBookStore.Book;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class BookControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;
    private String authorUrl;
    private String publisherUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/books";
        authorUrl = "http://localhost:" + port + "/api/authors";
        publisherUrl = "http://localhost:" + port + "/api/publishers";
    }

    @Test
    public void testGetAllBooks() {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        HttpEntity<Author> requestauthor1 = new HttpEntity<>(author1);
        ResponseEntity<Author> responseauthor1 = restTemplate.postForEntity(authorUrl, requestauthor1, Author.class);
        Author savedAuthor1 = responseauthor1.getBody();

        Publisher publisher1 = new Publisher();
        publisher1.setFirstName("First2");
        publisher1.setLastName("Last2");

        HttpEntity<Publisher> requestpublisher1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> responsepublisher1 = restTemplate.postForEntity(publisherUrl, requestpublisher1, Publisher.class);
        Publisher savedPublisher1 = responsepublisher1.getBody();

        // Create two Books
        Book book1 = new Book();
        book1.setAuthor(savedAuthor1);
        book1.setPublisher(savedPublisher1);
        book1.setDescription("This is the Description");
        book1.setIsbn(123556);
        book1.setPicture("picture/url");
        book1.setTitle("Lord of the Rings");
        book1.setDescription("Adventure story of a hobbit");
        HttpEntity<Book> request1 = new HttpEntity<>(book1);
        ResponseEntity<Book> response1 = restTemplate.postForEntity(baseUrl, request1, Book.class);
        Book savedBook1 = response1.getBody();

        Book book2 = new Book();
        book2.setAuthor(savedAuthor1);
        book2.setPublisher(savedPublisher1);
        book2.setDescription("This is the Description");
        book2.setIsbn(654321);
        book2.setPicture("picture/url");
        book2.setTitle("Star Wars");
        book2.setDescription("Adventure story of a Jedi");
        HttpEntity<Book> request2 = new HttpEntity<>(book2);
        ResponseEntity<Book> response2 = restTemplate.postForEntity(baseUrl, request2, Book.class);
        Book savedBook2 = response2.getBody();

        ResponseEntity<List<Book>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                });
        List<Book> books = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, books.size());
    }

    @Test
    public void testCreateBook() {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        HttpEntity<Author> requestauthor1 = new HttpEntity<>(author1);
        ResponseEntity<Author> responseauthor1 = restTemplate.postForEntity(authorUrl, requestauthor1, Author.class);
        Author savedAuthor1 = responseauthor1.getBody();

        Publisher publisher1 = new Publisher();
        publisher1.setFirstName("First2");
        publisher1.setLastName("Last2");

        HttpEntity<Publisher> requestpublisher1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> responsepublisher1 = restTemplate.postForEntity(publisherUrl, requestpublisher1, Publisher.class);
        Publisher savedPublisher1 = responsepublisher1.getBody();

        // Create two Books
        Book book = new Book();
        book.setAuthor(savedAuthor1);
        book.setPublisher(savedPublisher1);
        book.setDescription("This is the Description");
        book.setIsbn(123556);
        book.setPicture("picture/url");
        book.setTitle("Lord of the Rings");
        book.setDescription("Adventure story of a hobbit");
        HttpEntity<Book> request = new HttpEntity<>(book);
        System.out.println(request);
        ResponseEntity<Book> response = restTemplate.postForEntity(baseUrl, request, Book.class);
        Book savedBook = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(123556, savedBook.getIsbn());
        assertEquals("Lord of the Rings", savedBook.getTitle());
    }

    @Test
    public void testGetBookById() {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        HttpEntity<Author> requestauthor1 = new HttpEntity<>(author1);
        ResponseEntity<Author> responseauthor1 = restTemplate.postForEntity(authorUrl, requestauthor1, Author.class);
        Author savedAuthor1 = responseauthor1.getBody();

        Publisher publisher1 = new Publisher();
        publisher1.setFirstName("First2");
        publisher1.setLastName("Last2");

        HttpEntity<Publisher> requestpublisher1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> responsepublisher1 = restTemplate.postForEntity(publisherUrl, requestpublisher1, Publisher.class);
        Publisher savedPublisher1 = responsepublisher1.getBody();

        // Create two Books
        Book book1 = new Book();
        book1.setAuthor(savedAuthor1);
        book1.setPublisher(savedPublisher1);
        book1.setDescription("This is the Description");
        book1.setIsbn(123556);
        book1.setPicture("picture/url");
        book1.setTitle("Lord of the Rings");
        book1.setDescription("Adventure story of a hobbit");
        book1.setId(1L);
        HttpEntity<Book> request1 = new HttpEntity<>(book1);
        ResponseEntity<Book> response1 = restTemplate.postForEntity(baseUrl, request1, Book.class);
        Book savedBook1 = response1.getBody();

        ResponseEntity<Book> response = restTemplate.getForEntity(baseUrl + "/" + book1.getId(), Book.class);
        Book book = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(123556, book.getIsbn());
        assertEquals("Lord of the Rings", book.getTitle());
    }

    @Test
    public void testUpdateBookById() {
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        HttpEntity<Author> requestauthor1 = new HttpEntity<>(author1);
        ResponseEntity<Author> responseauthor1 = restTemplate.postForEntity(authorUrl, requestauthor1, Author.class);
        Author savedAuthor1 = responseauthor1.getBody();

        Publisher publisher1 = new Publisher();
        publisher1.setFirstName("First2");
        publisher1.setLastName("Last2");

        HttpEntity<Publisher> requestpublisher1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> responsepublisher1 = restTemplate.postForEntity(publisherUrl, requestpublisher1, Publisher.class);
        Publisher savedPublisher1 = responsepublisher1.getBody();

        // Create two Books
        Book book1 = new Book();
        book1.setAuthor(savedAuthor1);
        book1.setPublisher(savedPublisher1);
        book1.setDescription("This is the Description");
        book1.setIsbn(123556);
        book1.setPicture("picture/url");
        book1.setTitle("Lord of the Rings");
        book1.setDescription("Adventure story of a hobbit");
        book1.setId(1L);
        HttpEntity<Book> request1 = new HttpEntity<>(book1);
        ResponseEntity<Book> response1 = restTemplate.postForEntity(baseUrl, request1, Book.class);
        Book savedBook1 = response1.getBody();


        Book partialBook = new Book();
        partialBook.setAuthor(savedAuthor1);
        partialBook.setPublisher(savedPublisher1);
        partialBook.setDescription("This is the Description");
        partialBook.setIsbn(123556);
        partialBook.setPicture("picture/url");
        partialBook.setDescription("Adventure story of a hobbit");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> request = new HttpEntity<>(partialBook, headers);
        ResponseEntity<Book> response = restTemplate.exchange(
                baseUrl + "/" + book1.getId(),
                HttpMethod.PUT,
                request,
                Book.class);
        Book updatedBook = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(123556, updatedBook.getIsbn());
        assertEquals("Lord of the Rings", updatedBook.getTitle());
    }

    @Test
    public void testDeleteBook() {
        long bookId = 2;
        restTemplate.delete(baseUrl + "/" + bookId);
        ResponseEntity<Book> response = restTemplate.getForEntity(baseUrl + "/" + bookId, Book.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}