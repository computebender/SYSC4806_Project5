package ca.carleton.AmazinBookStore.Bookstore;

import ca.carleton.AmazinBookStore.Author.Author;
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
public class BookstoreControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/bookstores";
    }

    @Test
    public void testGetAllBookstores() {
        //create 2 bookstores
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setbookstoreName("My Bookstore");

        Bookstore bookstore2 = new Bookstore();
        bookstore2.setbookstoreName("Your Bookstore");

        HttpEntity<Bookstore> request1 = new HttpEntity<>(bookstore1);
        ResponseEntity<Bookstore> response1 = restTemplate.postForEntity(baseUrl, request1, Bookstore.class);
        Bookstore savedBookstore1 = response1.getBody();

        HttpEntity<Bookstore> request2 = new HttpEntity<>(bookstore2);
        ResponseEntity<Bookstore> response2 = restTemplate.postForEntity(baseUrl, request2, Bookstore.class);
        Bookstore savedBookstore2 = response2.getBody();

        ResponseEntity<List<Bookstore>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Bookstore>>() {
                });
        List<Bookstore> bookstores = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, bookstores.size());
    }

    @Test
    public void testCreateBookstore() {
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");

        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(baseUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());
    }

    @Test
    public void testGetBookstoreById() {
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setId(1L);
        bookstore1.setbookstoreName("Jane's Bookstore");
        HttpEntity<Bookstore> request1 = new HttpEntity<>(bookstore1);
        ResponseEntity<Bookstore> response1 = restTemplate.postForEntity(baseUrl, request1, Bookstore.class);
        Bookstore savedBookstore1 = response1.getBody();

        ResponseEntity<Bookstore> response = restTemplate.getForEntity(baseUrl + "/" + bookstore1.getId(), Bookstore.class);
        Bookstore bookstore = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Jane's Bookstore", bookstore.getbookstoreName());
    }

    @Test
    public void testUpdateBookstoreById() {
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setId(1L);
        bookstore1.setbookstoreName("Jane's Bookstore");

        HttpEntity<Bookstore> request1 = new HttpEntity<>(bookstore1);
        ResponseEntity<Bookstore> response1 = restTemplate.postForEntity(baseUrl, request1, Bookstore.class);
        Bookstore savedBookstore1 = response1.getBody();


        Bookstore partialBookstore = new Bookstore();
        partialBookstore.setbookstoreName("Janet's Bookstore");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Bookstore> request = new HttpEntity<>(partialBookstore, headers);
        ResponseEntity<Bookstore> response = restTemplate.exchange(
                baseUrl + "/" + bookstore1.getId(),
                HttpMethod.PUT,
                request,
                Bookstore.class);
        Bookstore updatedBookstore = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Janet's Bookstore", updatedBookstore.getbookstoreName());
    }

    @Test
    public void testDeleteBookstore() {
        long bookstoreId = 2;
        restTemplate.delete(baseUrl + "/" + bookstoreId);
        ResponseEntity<Bookstore> response = restTemplate.getForEntity(baseUrl + "/" + bookstoreId, Bookstore.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
