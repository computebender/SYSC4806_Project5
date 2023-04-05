package ca.carleton.AmazinBookStore.Authentication;

import ca.carleton.AmazinBookStore.User.User;
import ca.carleton.AmazinBookStore.User.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "jwt.secret=mytestsecret",
        "jwt.expiration=604800000"
})
public class AuthenticationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private HttpHeaders headers;

    private String authBaseUrl;

    private User testUser;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        authBaseUrl = "http://localhost:" + port + "/api/auth";

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
    public void testCreateUserAndAuthenticate() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(testUser.getEmail());
        jwtRequest.setPassword("password");

        HttpEntity<JwtRequest> request = new HttpEntity<>(jwtRequest, headers);
        ResponseEntity<JwtResponse> response = restTemplate.exchange(
                authBaseUrl, HttpMethod.POST, request, JwtResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtResponse jwtResponse = response.getBody();
        assertNotNull(jwtResponse.getToken());
    }
}
