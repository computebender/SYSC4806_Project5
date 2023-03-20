package ca.carleton.AmazinBookStore.Bookstore;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookstoreService {
    private final BookstoreRepository bookstoreRepository;
    private final ListingRepository listingRepository;

    public BookstoreService(BookstoreRepository bookstoreRepository, ListingRepository listingRepository) {
        this.bookstoreRepository = bookstoreRepository;
        this.listingRepository = listingRepository;
    }

    public List<Bookstore> findAll() { return this.bookstoreRepository.findAll(); }

    public Bookstore getBookstoreById(Long id){
        Optional<Bookstore> bookstore = this.bookstoreRepository.findById(id);

        if(bookstore.isEmpty()){
            throw new ResourceNotFoundException("Bookstore with ID " + id + " not found.");
        }

        return bookstore.get();
    }

    public Bookstore createBookstore(Bookstore bookstore){
        return this.bookstoreRepository.save(bookstore);
    }

    public Bookstore updateBookstore(Long id, Bookstore partialBookstore) {
        Optional<Bookstore> optionalBookstore = this.bookstoreRepository.findById(id);
        if(optionalBookstore.isEmpty()){
            throw new ResourceNotFoundException("Bookstore with ID " + id + " not found.");
        }
        Bookstore bookstore = optionalBookstore.get();

        if(Objects.nonNull(partialBookstore.getbookstoreName())){
            bookstore.setbookstoreName(partialBookstore.getbookstoreName());
        }

        this.bookstoreRepository.save(bookstore);

        return bookstore;
    }

    public void deleteBookstore(Long id) {
        Optional<Bookstore> optionalBookstore = this.bookstoreRepository.findById(id);
        if(optionalBookstore.isEmpty()){
            throw new ResourceNotFoundException("Bookstore with ID " + id + " not found.");
        }
        this.bookstoreRepository.deleteById(optionalBookstore.get().getId());
    }

    public Bookstore createListing(Long id, Listing listing){
        Optional<Bookstore> optionalBookstore = this.bookstoreRepository.findById(id);
        if(optionalBookstore.isEmpty()){
            throw new ResourceNotFoundException("Bookstore with ID " + id + " not found.");
        }
        Bookstore bookstore = optionalBookstore.get();

        bookstore.addListing(listing);
        this.listingRepository.save(listing);
        this.bookstoreRepository.save(bookstore);

        return bookstore;
    }

    public List<Listing> getAllListings(Long bookstoreId){
        Optional<Bookstore> optionalBookstore = this.bookstoreRepository.findById(bookstoreId);
        if(optionalBookstore.isEmpty()){
            throw new ResourceNotFoundException("Bookstore with ID " + bookstoreId + " not found.");
        }
        Bookstore bookstore = optionalBookstore.get();
        List<Listing> listings = bookstore.getListings();
        return listings;
    }

    public Listing getListingById(Long id, Bookstore bookstore) {
        Listing matchListing;
        Boolean matchingId = false;
        for (Listing listing : bookstore.getListings()) {
            if (listing.getId().equals(id)) {
                matchListing = listing;
                return matchListing;
            }
        }

        throw new ResourceNotFoundException("Listing with ID " + id + " not found in bookstore with ID" + bookstore.getId());
    }

    public Listing updateListingById(Long id, Bookstore bookstore, Listing partialListing){
        Listing listing = getListingById(id, bookstore);
        //Bookstore bookstore = deleteListingById(id, bookstore);

        if(Objects.nonNull(partialListing.getLocation())){
            listing.setLocation(partialListing.getLocation());
        }
        if(Objects.nonNull(partialListing.getPrice())){
            listing.setPrice(partialListing.getPrice());
        }
        if(Objects.nonNull(partialListing.getCopies())){
            listing.setCopies(partialListing.getCopies());
        }
        if(Objects.nonNull(partialListing.getTitle())){
            listing.setTitle(partialListing.getTitle());
        }
        Bookstore updatedBookstore = createListing(id, listing);
        this.bookstoreRepository.save(updatedBookstore);
        this.listingRepository.save(listing);
    return listing;
    }

    public void deleteListingById(Long id, Bookstore bookstore){
        bookstore.removeListing(id);
        this.bookstoreRepository.save(bookstore);
        this.listingRepository.deleteById(id);
    }

}
