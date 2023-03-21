package ca.carleton.AmazinBookStore.Listing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreRepository;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreService;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingService listingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Listing> listings = new ArrayList<>();
        listings.add(new Listing());
        listings.add(new Listing());
        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> result = listingService.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testCreateListing() {
        Listing listing = new Listing();
        when(listingRepository.save(listing)).thenReturn(listing);

        Listing result = listingService.createListing(listing);

        Assertions.assertEquals(listing, result);
    }

    @Test
    public void testGetListingById() {
        Listing listing = new Listing();
        listing.setId(1L);
        Optional<Listing> optionalListing = Optional.of(listing);
        when(listingRepository.findById(anyLong())).thenReturn(optionalListing);

        Listing result = listingService.getListingById(1L);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    public void testUpdateListing() {
        Listing listing = new Listing();
        listing.setId(1L);
        Optional<Listing> optionalListing = Optional.of(listing);
        when(listingRepository.findById(anyLong())).thenReturn(optionalListing);
        when(listingRepository.save(listing)).thenReturn(listing);

        Listing result = listingService.updateListingById(1L, listing);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    public void testGetListingByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> listingService.deleteListingById(1L));
    }

}
