package ca.carleton.AmazinBookStore.Listing;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

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
}
