package ca.carleton.AmazinBookStore.ShoppingCart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ShoppingCartControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/carts";
    }

    @Test
    public void createShoppingCart(){
        String userId = "test";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
    }

    @Test
    public void getAllShoppingCarts(){
        // Create 3 shopping carts
        String userId1 = "user1";
        String userId2 = "user2";
        String userId3 = "user3";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId1, request1, ShoppingCart.class);
        ResponseEntity<ShoppingCart> response2 = restTemplate.postForEntity(baseUrl + "/" +userId2, request1, ShoppingCart.class);
        ResponseEntity<ShoppingCart> response3 = restTemplate.postForEntity(baseUrl + "/" +userId3, request1, ShoppingCart.class);

        ResponseEntity<List<ShoppingCart>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ShoppingCart>>() {
                });
        List<ShoppingCart> shoppingCarts = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, shoppingCarts.size());
    }

    public void addItem(){

    }

}
