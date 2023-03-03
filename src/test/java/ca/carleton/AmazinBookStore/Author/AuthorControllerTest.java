package ca.carleton.AmazinBookStore.Author;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class AuthorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/authors";
    }

    @Test
    public void testGetAllAuthors() {
        // Create two authors
        Author author1 = new Author();
        author1.setFirstName("John");
        author1.setLastName("Doe");
        HttpEntity<Author> request1 = new HttpEntity<>(author1);
        ResponseEntity<Author> response1 = restTemplate.postForEntity(baseUrl, request1, Author.class);
        Author savedAuthor1 = response1.getBody();

        Author author2 = new Author();
        author2.setFirstName("Jane");
        author2.setLastName("Doe");
        HttpEntity<Author> request2 = new HttpEntity<>(author2);
        ResponseEntity<Author> response2 = restTemplate.postForEntity(baseUrl, request2, Author.class);
        Author savedAuthor2 = response2.getBody();

        ResponseEntity<List<Author>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Author>>() {
                });
        List<Author> authors = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, authors.size());
    }

    @Test
    public void testCreateAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        HttpEntity<Author> request = new HttpEntity<>(author);
        ResponseEntity<Author> response = restTemplate.postForEntity(baseUrl, request, Author.class);
        Author savedAuthor = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", savedAuthor.getFirstName());
        assertEquals("Doe", savedAuthor.getLastName());
    }

    @Test
    public void testGetAuthorById() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setFirstName("Jane");
        author1.setLastName("Doe");
        HttpEntity<Author> request1 = new HttpEntity<>(author1);
        ResponseEntity<Author> response1 = restTemplate.postForEntity(baseUrl, request1, Author.class);
        Author savedAuthor1 = response1.getBody();

        ResponseEntity<Author> response = restTemplate.getForEntity(baseUrl + "/" + author1.getId(), Author.class);
        Author author = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane", author.getFirstName());
        assertEquals("Doe", author.getLastName());
    }

    @Test
    public void testUpdateAuthorById() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setFirstName("Jane");
        author1.setLastName("Doe");
        HttpEntity<Author> request1 = new HttpEntity<>(author1);
        ResponseEntity<Author> response1 = restTemplate.postForEntity(baseUrl, request1, Author.class);
        Author savedAuthor1 = response1.getBody();


        Author partialAuthor = new Author();
        partialAuthor.setFirstName("Janet");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Author> request = new HttpEntity<>(partialAuthor, headers);
        ResponseEntity<Author> response = restTemplate.exchange(
                baseUrl + "/" + author1.getId(),
                HttpMethod.PUT,
                request,
                Author.class);
        Author updatedAuthor = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Janet", updatedAuthor.getFirstName());
        assertEquals("Doe", updatedAuthor.getLastName());
    }

    @Test
    public void testDeleteAuthor() {
        long authorId = 2;
        restTemplate.delete(baseUrl + "/" + authorId);
        ResponseEntity<Author> response = restTemplate.getForEntity(baseUrl + "/" + authorId, Author.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
