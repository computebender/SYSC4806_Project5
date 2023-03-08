package ca.carleton.AmazinBookStore.Bookstore;

import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@RestController
@RequestMapping("/api/bookstores")
public class BookstoreController {
    private final BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService) { this.bookstoreService = bookstoreService; }

    @GetMapping()
    public ResponseEntity<List<Bookstore>> getAllBookstores(){
        List<Bookstore> bookstores = this.bookstoreService.findAll();
        return ResponseEntity.ok(bookstores);
    }

    @PostMapping()
    public ResponseEntity<Bookstore> createBookstore(@RequestBody Bookstore bookstore){
        Bookstore savedBookstore = this.bookstoreService.createBookstore(bookstore);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookstore);
    }

    @GetMapping("/{bookstoreId}")
    public ResponseEntity<Bookstore> getBookstoreById(@PathVariable long bookstoreId) {
        Bookstore bookstore;
        try {
            bookstore = this.bookstoreService.getBookstoreById(bookstoreId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bookstore);
    }

    @PutMapping("/{bookstoreId}")
    public ResponseEntity<Bookstore> updateBookstoreById(@PathVariable long bookstoreId, @RequestBody Bookstore partialBookstore) {
        Bookstore bookstore;
        try {
            bookstore = bookstoreService.updateBookstore(bookstoreId, partialBookstore);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookstore);
    }

    @DeleteMapping("/{bookstoreId}")
    public ResponseEntity<Void> deleteBookstore(@PathVariable Long bookstoreId) {
        try {
            bookstoreService.deleteBookstore(bookstoreId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{bookstoreId}/listings")
    public ResponseEntity<Bookstore> createListing(@PathVariable long bookstoreId, @RequestBody Listing listing) {
        Bookstore bookstore;
        try {
            bookstore = bookstoreService.createListing(bookstoreId, listing);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookstore);
    }

    @GetMapping("/{bookstoreId}/listings/{listingId}")
    public ResponseEntity<Listing> getListingById(@PathVariable long bookstoreId, @PathVariable long listingId) {
        Bookstore bookstore;
        Listing listing;
        try {
            bookstore = this.bookstoreService.getBookstoreById(bookstoreId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        try {
            listing = this.bookstoreService.getListingById(listingId, bookstore);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listing);
    }

    @PutMapping("/{bookstoreId}/listings/{listingId}")
    public ResponseEntity<Listing> updateListingById(@PathVariable long bookstoreId, @PathVariable long listingId, @RequestBody Listing partialListing) {
        Bookstore bookstore;
        Listing listing;
        try {
            bookstore = this.bookstoreService.getBookstoreById(bookstoreId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        try {
            listing = bookstoreService.updateListingById(listingId, bookstore, partialListing);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listing);
    }

    @DeleteMapping("/{bookstoreId}/listings/{listingId}")
    public ResponseEntity<Void> deleteListingById(@PathVariable long bookstoreId, @PathVariable long listingId) {
        Bookstore bookstore;
        try {
            bookstore = this.bookstoreService.getBookstoreById(bookstoreId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        this.bookstoreService.deleteListingById(listingId, bookstore);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookstoreId}/listings/test")
    public ResponseEntity<Void> test() {
        String str = "Hello";
        return ResponseEntity.noContent().build();
    }

}
