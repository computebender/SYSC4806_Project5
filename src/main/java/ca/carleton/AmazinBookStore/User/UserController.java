package ca.carleton.AmazinBookStore.User;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Listing.ListingService;
import ca.carleton.AmazinBookStore.User.BookRecommendation.BookRecommendation;
import ca.carleton.AmazinBookStore.User.BookRecommendation.BookRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private ListingService listingService;
    private BookRecommendationService bookRecommendationService;

    @Autowired
    public UserController(UserService userService, BookRecommendationService bookRecommendationService, ListingService listingService) {
        this.userService = userService;
        this.bookRecommendationService= bookRecommendationService;
        this.listingService = listingService;
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        List<Listing> purchaseHistory = new ArrayList<>();
        for (Listing l: user.getPurchaseHistory()) {
            Listing listing = listingService.getListingById(l.getId());
            purchaseHistory.add(listing);
        }

        user.setPurchaseHistory(purchaseHistory);

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user;
        try {
            user = userService.getUserById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<BookRecommendation> getBookRecommendationById(@PathVariable Long id) {
        BookRecommendation recommendations;
        try {
            recommendations = bookRecommendationService.getRecommendationById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recommendations);
    }

    /*
    Temp endpoint to be used until login works
    TODO: remove
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User partialUser) {
        User user;
        try {
            user = userService.updateUser(id, partialUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

