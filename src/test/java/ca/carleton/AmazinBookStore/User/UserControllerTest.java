package ca.carleton.AmazinBookStore.User;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;

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
        testUser.setPurchaseHistory(new ArrayList<>());
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
        newUser.setPurchaseHistory(new ArrayList<>());

        TestObjectMapper objectMapper = new TestObjectMapper();
        try {
            String newUserJson = objectMapper.writeValueAsString(newUser);
            HttpEntity<String> request = new HttpEntity<>(newUserJson, headers);
            ResponseEntity<User> response = restTemplate.exchange(
                    baseUrl, HttpMethod.POST, request, User.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            User createdUser = response.getBody();
            assertNotNull(createdUser.getId());
            assertEquals(newUser.getFirstName(), createdUser.getFirstName());
            assertEquals(newUser.getLastName(), createdUser.getLastName());
            assertEquals(newUser.getEmail(), createdUser.getEmail());
        } catch (JsonProcessingException e) {
            System.out.println("Failed to serialize user object: " + e.getMessage());
        }
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

    public class TestObjectMapper extends ObjectMapper {
        public TestObjectMapper() {
            super();
            this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
            this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            this.addMixIn(User.class, UserMixin.class);
        }

        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
        abstract class UserMixin {
            @JsonProperty
            abstract String getPassword();
        }
    }

}
