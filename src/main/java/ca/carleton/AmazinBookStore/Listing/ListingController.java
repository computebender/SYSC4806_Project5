package ca.carleton.AmazinBookStore.Listing;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Book.BookService;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreService;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
@RestController
@RequestMapping("/api/listings")
public class ListingController {
    private final ListingService listingService;
    private final BookService bookService;
    private final BookstoreService bookstoreService;

    public ListingController(ListingService listingService, BookService bookService, BookstoreService bookstoreService) {
        this.listingService = listingService;
        this.bookService = bookService;
        this.bookstoreService = bookstoreService;
    }

    @GetMapping()
    public ResponseEntity<List<Listing>> getAllListings(){
        List<Listing> listings = this.listingService.findAll();
        return ResponseEntity.ok(listings);
    }

    @PostMapping()
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing){
        Book book = bookService.getBookById(listing.getBook().getId());
        Bookstore bookstore = bookstoreService.getBookstoreById(listing.getLocation().getId());

        listing.setBook(book);
        listing.setLocation(bookstore);

        Listing savedListing = this.listingService.createListing(listing);
        bookstore.addListing(listing);
        bookstoreService.updateBookstore(bookstore.getId(), bookstore);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedListing);
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<Listing> getListingById(@PathVariable long listingId) {
        Listing listing;
        try {
            listing = this.listingService.getListingById(listingId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(listing);
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<Listing> updateListingById(@PathVariable long listingId, @RequestBody Listing partialListing) {
        Listing listing;
        try {
            listing = listingService.updateListingById(listingId, partialListing);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listing);
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long listingId) {
        try {
            listingService.deleteListingById(listingId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/search")
    public ResponseEntity<List<Listing>> searchListings(@Param("bookstoreId") Long bookstoreId, @Param("authorId") Long authorId,
                                                        @Param("genreId") Long genreId, @Param("bookId") Long bookId) {
        List<Listing> listings;
        try {
            listings = this.listingService.searchListings(bookstoreId, authorId, genreId, bookId);
        }  catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listings);
    }
}
