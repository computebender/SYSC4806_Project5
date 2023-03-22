package ca.carleton.AmazinBookStore.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private HttpHeaders headers;

    private String baseUrl;

    private User testUser;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        baseUrl = "http://localhost:" + port + "/api/users";

        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("johndoe@example.com");
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() {
        User newUser = new User();
        newUser.setFirstName("Jane");
        newUser.setLastName("Doe");
        newUser.setEmail("janedoe@example.com");
        newUser.setPassword("password");

        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<User> response = restTemplate.exchange(
                baseUrl, HttpMethod.POST, request, User.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        User createdUser = response.getBody();
        assertNotNull(createdUser.getId());
        assertEquals(newUser.getFirstName(), createdUser.getFirstName());
        assertEquals(newUser.getLastName(), createdUser.getLastName());
        assertEquals(newUser.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testGetUserById() {
        ResponseEntity<User> response = restTemplate.getForEntity(
                baseUrl + "/" + testUser.getId(), User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getLastName(), user.getLastName());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test
    public void testGetUserByEmail() {
        ResponseEntity<User> response = restTemplate.getForEntity(
                baseUrl + "/email/{email}", User.class, testUser.getEmail());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getLastName(), user.getLastName());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test
    public void testGetAllUsers() {
        User newUser1 = new User();
        newUser1.setFirstName("Jane");
        newUser1.setLastName("Doe");
        newUser1.setEmail("janedoe@example.com");
        newUser1.setPassword("password");

        User newUser2 = new User();
        newUser2.setFirstName("Bob");
        newUser2.setLastName("Smith");
        newUser2.setEmail("bobsmith@example.com");
        newUser2.setPassword("password");

        userRepository.saveAll(Arrays.asList(newUser1, newUser2));

        ResponseEntity<User[]> response = restTemplate.getForEntity(
                baseUrl, User[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<User> users = Arrays.asList(response.getBody());
        assertEquals(3, users.size());
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setEmail("janedoe@example.com");
        updatedUser.setPassword("newpassword");

        HttpEntity<User> request = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<User> response = restTemplate.exchange(
                baseUrl + "/" + testUser.getId(), HttpMethod.PUT, request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(testUser.getId(), user.getId());
        assertEquals(updatedUser.getFirstName(), user.getFirstName());
        assertEquals(updatedUser.getLastName(), user.getLastName());
        assertEquals(updatedUser.getEmail(), user.getEmail());
        assertEquals(updatedUser.getPassword(), user.getPassword());
    }

    @Test
    public void testDeleteUserById() {
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + testUser.getId(), HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<User> getUserResponse = restTemplate.getForEntity(
                baseUrl + "/" + testUser.getId(), User.class);

        assertEquals(HttpStatus.NOT_FOUND, getUserResponse.getStatusCode());
    }
}
