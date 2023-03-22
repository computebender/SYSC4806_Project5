package ca.carleton.AmazinBookStore.User;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Listing.ListingService;
import ca.carleton.AmazinBookStore.User.BookRecommendation.BookRecommendation;
import ca.carleton.AmazinBookStore.User.BookRecommendation.BookRecommendationService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<User> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        User user;
        try {
            user = userService.getUserByEmail(currentUserEmail);
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

