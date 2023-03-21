package ca.carleton.AmazinBookStore.ShoppingCart;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Listing.Listing;
import jakarta.transaction.Transactional;
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
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
    }

    @Test
    public void getShoppingCartById(){
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);

        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);
        assertEquals(HttpStatus.OK ,response_cart.getStatusCode());
        assertEquals(userId ,response_cart.getBody().getUserId());
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
    @Test
    public void addItem(){
        //Create Author
        //Create Publisher
        //Create Genre
        //Create Book
        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());
        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Harry Potter");
        listing.setCopies("10");
        listing.setPrice("20.00");
        HttpEntity<Listing> request2 = new HttpEntity<>(listing);
        ResponseEntity<Bookstore> response2 = restTemplate.postForEntity(bookstoreUrl + "/" + bookstoreId +"/listings", request2, Bookstore.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(bookstoreUrl + "/" + bookstoreId +"/listings/" + listing.getId(), Listing.class);
        Listing listing1 = response3.getBody();
        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing1);
        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getTitle(), listing.getTitle());
    }

    @Test
    public void removeItem(){
        //Create Author
        //Create Publisher
        //Create Genre
        //Create Book
        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());
        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Harry Potter");
        listing.setCopies("10");
        listing.setPrice("20.00");
        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setId(2L);
        listing2.setTitle("1989 - Taylor Swift");
        listing2.setCopies("5");
        listing2.setPrice("25.00");

        HttpEntity<Listing> request2 = new HttpEntity<>(listing);
        HttpEntity<Listing> request5 = new HttpEntity<>(listing2);

        ResponseEntity<Bookstore> response2 = restTemplate.postForEntity(bookstoreUrl + "/" + bookstoreId +"/listings", request2, Bookstore.class);
        ResponseEntity<Bookstore> response5 = restTemplate.postForEntity(bookstoreUrl + "/" + bookstoreId +"/listings", request5, Bookstore.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(bookstoreUrl + "/" + bookstoreId +"/listings/" + listing.getId(), Listing.class);
        Listing listing_1 = response3.getBody();
        ResponseEntity<Listing> response7 = restTemplate.getForEntity(bookstoreUrl + "/" + bookstoreId +"/listings/" + listing2.getId(), Listing.class);
        Listing listing_2 = response7.getBody();
        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing_1);
        HttpEntity<Listing> request6 = new HttpEntity<>(listing_2);

        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getTitle(), listing.getTitle());
        ResponseEntity<ShoppingCart> response6 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request6, ShoppingCart.class);
        assertEquals(response6.getBody().getItems().get(1).getBookListing().getTitle(), listing2.getTitle());
        //Get shopping cart
        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);

        //Remove item from shopping cart
        assertEquals(response_cart.getBody().getItems().size(), 2);
        CartItem item1 = response6.getBody().getItems().get(0);
        item1.setId(1L);
        CartItem item2 = response4.getBody().getItems().get(0);
        item1.setId(2L);
        HttpEntity<Long> request7 = new HttpEntity<>(item1.getId());
        HttpEntity<Long> request8 = new HttpEntity<>(item2.getId());

        //Remove CartItem 1
        ResponseEntity resp = restTemplate.exchange(baseUrl + "/" + userId +"/remove-item", HttpMethod.DELETE, request7, Void.class);
        response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);
        assertEquals(1,response_cart.getBody().getItems().size());
        //Remove CartItem 2
        ResponseEntity resp2 = restTemplate.exchange(baseUrl + "/" + userId +"/remove-item", HttpMethod.DELETE, request8, Void.class);
        response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);
        assertEquals( 0,response_cart.getBody().getItems().size());
    }
    @Test
    public void clearCart(){
        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());
        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Harry Potter");
        listing.setCopies("10");
        listing.setPrice("20.00");
        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setId(2L);
        listing2.setTitle("1989 - Taylor Swift");
        listing2.setCopies("5");
        listing2.setPrice("25.00");

        HttpEntity<Listing> request2 = new HttpEntity<>(listing);
        HttpEntity<Listing> request5 = new HttpEntity<>(listing2);

        ResponseEntity<Bookstore> response2 = restTemplate.postForEntity(bookstoreUrl + "/" + bookstoreId +"/listings", request2, Bookstore.class);
        ResponseEntity<Bookstore> response5 = restTemplate.postForEntity(bookstoreUrl + "/" + bookstoreId +"/listings", request5, Bookstore.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(bookstoreUrl + "/" + bookstoreId +"/listings/" + listing.getId(), Listing.class);
        Listing listing_1 = response3.getBody();
        ResponseEntity<Listing> response7 = restTemplate.getForEntity(bookstoreUrl + "/" + bookstoreId +"/listings/" + listing2.getId(), Listing.class);
        Listing listing_2 = response7.getBody();
        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing_1);
        HttpEntity<Listing> request6 = new HttpEntity<>(listing_2);

        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getTitle(), listing.getTitle());
        ResponseEntity<ShoppingCart> response6 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request6, ShoppingCart.class);
        assertEquals(response6.getBody().getItems().get(1).getBookListing().getTitle(), listing2.getTitle());
        //Get shopping cart
        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);

        assertEquals(response_cart.getBody().getItems().size(), 2);
        ResponseEntity<ShoppingCart> response9 = restTemplate.postForEntity(baseUrl + "/" + userId +"/clear", new HttpEntity<String>(""), ShoppingCart.class);
        assertEquals(HttpStatus.CREATED,response9.getStatusCode());
        assertEquals(0,response9.getBody().getItems().size());
    }

    @Test
    @Transactional
    public void checkoutCart(){
        //Create Bookstore
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        String bookstoreUrl = "http://localhost:" + port + "/api/bookstores";
        HttpEntity<Bookstore> request = new HttpEntity<>(bookstore);
        ResponseEntity<Bookstore> response = restTemplate.postForEntity(bookstoreUrl, request, Bookstore.class);
        Bookstore savedBookstore = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John's Bookstore", savedBookstore.getbookstoreName());
        //Create ShoppingCart
        String userId = "user";
        HttpEntity<String> request1 = new HttpEntity<>("");
        ResponseEntity<ShoppingCart> response1 = restTemplate.postForEntity(baseUrl + "/" +userId, request1, ShoppingCart.class);
        ShoppingCart cartResponse = response1.getBody();
        assertEquals(cartResponse.getItems().size(),0);
        assertEquals(cartResponse.getUserId(), userId);
        //Create Listing
        String bookstoreId = "1";
        Listing listing = new Listing();
        listing.setId(1L);
        listing.setTitle("Harry Potter");
        listing.setCopies("10");
        listing.setPrice("20.00");
        //Create 2nd listing
        Listing listing2 = new Listing();
        listing2.setId(2L);
        listing2.setTitle("1989 - Taylor Swift");
        listing2.setCopies("5");
        listing2.setPrice("25.00");

        HttpEntity<Listing> request2 = new HttpEntity<>(listing);
        HttpEntity<Listing> request5 = new HttpEntity<>(listing2);

        ResponseEntity<Bookstore> response2 = restTemplate.postForEntity(bookstoreUrl + "/" + bookstoreId +"/listings", request2, Bookstore.class);
        ResponseEntity<Bookstore> response5 = restTemplate.postForEntity(bookstoreUrl + "/" + bookstoreId +"/listings", request5, Bookstore.class);

        ResponseEntity<Listing> response3 = restTemplate.getForEntity(bookstoreUrl + "/" + bookstoreId +"/listings/" + listing.getId(), Listing.class);
        Listing listing_1 = response3.getBody();
        ResponseEntity<Listing> response7 = restTemplate.getForEntity(bookstoreUrl + "/" + bookstoreId +"/listings/" + listing2.getId(), Listing.class);
        Listing listing_2 = response7.getBody();
        //Add Listing to ShoppingCart
        HttpEntity<Listing> request4 = new HttpEntity<>(listing_1);
        HttpEntity<Listing> request6 = new HttpEntity<>(listing_2);

        ResponseEntity<ShoppingCart> response4 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request4, ShoppingCart.class);
        assertEquals(response4.getBody().getItems().get(0).getBookListing().getTitle(), listing.getTitle());
        ResponseEntity<ShoppingCart> response6 = restTemplate.postForEntity(baseUrl + "/" + userId +"/add-item", request6, ShoppingCart.class);
        assertEquals(response6.getBody().getItems().get(1).getBookListing().getTitle(), listing2.getTitle());
        //Get shopping cart
        ResponseEntity<ShoppingCart> response_cart = restTemplate.getForEntity(baseUrl + "/" + userId, ShoppingCart.class);

        assertEquals(response_cart.getBody().getItems().size(), 2);
        //checkout shopping cart
        ResponseEntity<Double> response9 = restTemplate.postForEntity(baseUrl + "/" + userId +"/checkout", new HttpEntity<String>(""), Double.class);
        assertEquals(HttpStatus.CREATED,response9.getStatusCode());
        assertEquals(325,response9.getBody());
    }

}
