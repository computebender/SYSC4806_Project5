package ca.carleton.AmazinBookStore.Genres;

import ca.carleton.AmazinBookStore.Genre.Genre;
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

public class GenresControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/genres";
    }

    @Test
    public void testGetAllGenres() {
        // Create two Genres
        Genre genre1 = new Genre();
        genre1.setName("Fiction");
        HttpEntity<Genre> request1 = new HttpEntity<>(genre1);
        ResponseEntity<Genre> response1 = restTemplate.postForEntity(baseUrl, request1, Genre.class);
        Genre savedGenre1 = response1.getBody();

        Genre genre2 = new Genre();
        genre2.setName("Fiction");
        HttpEntity<Genre> request2 = new HttpEntity<>(genre2);
        ResponseEntity<Genre> response2 = restTemplate.postForEntity(baseUrl, request2, Genre.class);
        Genre savedGenre2 = response2.getBody();

        ResponseEntity<List<Genre>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Genre>>() {
                });
        List<Genre> genres = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, genres.size());
    }

    @Test
    public void testCreateGenre() {
        Genre genre = new Genre();
        genre.setName("Fiction");
        HttpEntity<Genre> request = new HttpEntity<>(genre);
        ResponseEntity<Genre> response = restTemplate.postForEntity(baseUrl, request, Genre.class);
        Genre savedGenre = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Fiction", savedGenre.getName());
    }

    @Test
    public void testGetGenreById() {
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");
        HttpEntity<Genre> request1 = new HttpEntity<>(genre1);
        ResponseEntity<Genre> response1 = restTemplate.postForEntity(baseUrl, request1, Genre.class);
        Genre savedGenre1 = response1.getBody();

        ResponseEntity<Genre> response = restTemplate.getForEntity(baseUrl + "/" + genre1.getId(), Genre.class);
        Genre genre = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Fiction", genre.getName());
    }

    @Test
    public void testUpdateGenreById() {
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Fiction");
        HttpEntity<Genre> request1 = new HttpEntity<>(genre1);
        ResponseEntity<Genre> response1 = restTemplate.postForEntity(baseUrl, request1, Genre.class);
        Genre savedGenre1 = response1.getBody();


        Genre partialGenre = new Genre();
        partialGenre.setName("Non-Fiction");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Genre> request = new HttpEntity<>(partialGenre, headers);
        ResponseEntity<Genre> response = restTemplate.exchange(
                baseUrl + "/" + genre1.getId(),
                HttpMethod.PUT,
                request,
                Genre.class);
        Genre updatedGenre = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Non-Fiction", updatedGenre.getName());
    }

    @Test
    public void testDeleteGenre() {
        long genreId = 2;
        restTemplate.delete(baseUrl + "/" + genreId);
        ResponseEntity<Genre> response = restTemplate.getForEntity(baseUrl + "/" + genreId, Genre.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
