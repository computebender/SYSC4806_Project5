package ca.carleton.AmazinBookStore.Listing;

import ca.carleton.AmazinBookStore.Genre.Genre;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> findAll() { return this.listingRepository.findAll(); }

    public Listing getListingById(Long id) {
        Optional<Listing> listing = this.listingRepository.findById(id);

        if(listing.isEmpty()){
            throw new ResourceNotFoundException("Listing with ID " + id + " not found.");
        }

        return listing.get();
    }

    public Listing createListing(Listing listing){
        return this.listingRepository.save(listing);
    }

    public Listing updateListingById(Long id, Listing partialListing){
        Optional<Listing> optionalListing = this.listingRepository.findById(id);
        if(optionalListing.isEmpty()){
            throw new ResourceNotFoundException("Listing with ID " + id + " not found.");
        }
        Listing listing = optionalListing.get();

        if(Objects.nonNull(partialListing.getLocation())){
            listing.setLocation(partialListing.getLocation());
        }
        if(Objects.nonNull(partialListing.getPrice())){
            listing.setPrice(partialListing.getPrice());
        }
        if(Objects.nonNull(partialListing.getCopies())){
            listing.setCopies(partialListing.getCopies());
        }
        if(Objects.nonNull(partialListing.getBook())){
            listing.setBook(partialListing.getBook());
        }
        this.listingRepository.save(listing);
        return listing;
    }

    public void deleteListingById(Long id){
        Optional<Listing> optionalListing = this.listingRepository.findById(id);
        if(optionalListing.isEmpty()){
            throw new ResourceNotFoundException("Listing with ID " + id + " not found.");
        }
        this.listingRepository.deleteById(optionalListing.get().getId());
    }

    public List<Listing> searchListings(Long bookstoreId, Long authorId, Long genreId, Long bookId) {
        List<Listing> listings = this.listingRepository.findAll();
        List<Listing> listingsMatch = this.listingRepository.findAll();

        for (Listing listing : listings) {
            if (!Objects.equals(listing.getLocation().getId(), bookstoreId) && bookstoreId != 0L) {
                listingsMatch.remove(listing);
                continue;
            }
            if (!Objects.equals(listing.getBook().getAuthor().getId(), authorId) && authorId != 0L) {
                listingsMatch.remove(listing);
                continue;
            }
            if (!Objects.equals(listing.getBook().getId(), bookId) && bookId != 0L) {
                listingsMatch.remove(listing);
                continue;
            }
            List<Genre> tempGenre = listing.getBook().getGenres();
            boolean genreMatch = false;
            for (Genre genre : tempGenre) {
                if (Objects.equals(genre.getId(), genreId)) {
                    genreMatch = true;
                }
            }
            if (!genreMatch && genreId != 0) {
                listingsMatch.remove(listing);
                continue;
            }
        }
        if(listings.isEmpty()){
            throw new ResourceNotFoundException("No Listings with matching parameters.");
        }
        return listingsMatch;
    }

}
