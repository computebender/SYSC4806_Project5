package ca.carleton.AmazinBookStore.Publisher;

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

public class PublisherControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/publishers";
    }

    @Test
    public void testGetAllPublishers() {
        // Initialize 2 different Publisher Objects
        Publisher publisher1 = new Publisher();
        publisher1.setName("First");

        Publisher publisher2 = new Publisher();
        publisher2.setName("First2");

        HttpEntity<Publisher> request1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> response1 = restTemplate.postForEntity(baseUrl, request1, Publisher.class);
        Publisher savedPublisher1 = response1.getBody();

        HttpEntity<Publisher> request2 = new HttpEntity<>(publisher2);
        ResponseEntity<Publisher> response2 = restTemplate.postForEntity(baseUrl, request2, Publisher.class);
        Publisher savedPublisher2 = response2.getBody();

        ResponseEntity<List<Publisher>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Publisher>>() {
                });
        List<Publisher> publishers = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, publishers.size());
    }

    @Test
    public void testCreatePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("First");

        HttpEntity<Publisher> request = new HttpEntity<>(publisher);
        ResponseEntity<Publisher> response = restTemplate.postForEntity(baseUrl, request, Publisher.class);
        Publisher savedPublisher = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("First", savedPublisher.getName());
    }

    @Test
    public void testGetPublisherById() {
        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);
        publisher1.setName("First");
        HttpEntity<Publisher> request1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> response1 = restTemplate.postForEntity(baseUrl, request1, Publisher.class);
        Publisher savedPublisher1 = response1.getBody();

        ResponseEntity<Publisher> response = restTemplate.getForEntity(baseUrl + "/" + publisher1.getId(), Publisher.class);
        Publisher publisher = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("First", publisher.getName());
    }

    @Test
    public void testUpdatePublisherById() {
        Publisher publisher1 = new Publisher();
        publisher1.setId(1L);
        publisher1.setName("First");
        HttpEntity<Publisher> request1 = new HttpEntity<>(publisher1);
        ResponseEntity<Publisher> response1 = restTemplate.postForEntity(baseUrl, request1, Publisher.class);
        Publisher savedPublisher1 = response1.getBody();


        Publisher partialPublisher = new Publisher();
        partialPublisher.setName("Test");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Publisher> request = new HttpEntity<>(partialPublisher, headers);
        ResponseEntity<Publisher> response = restTemplate.exchange(baseUrl + "/" + publisher1.getId(), HttpMethod.PUT, request, Publisher.class);
        Publisher updatedPublisher = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test", updatedPublisher.getName());
    }

    @Test
    public void testRemovePublisher() {
        long publisherId = 2;
        restTemplate.delete(baseUrl + "/" + publisherId);
        ResponseEntity<Publisher> response = restTemplate.getForEntity(baseUrl + "/" + publisherId, Publisher.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
