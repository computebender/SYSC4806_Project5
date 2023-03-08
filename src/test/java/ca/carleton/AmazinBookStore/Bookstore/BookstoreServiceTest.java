package ca.carleton.AmazinBookStore.Bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AmazinBookStoreApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookstoreServiceTest {

    @Autowired
    private BookstoreRepository bookstoreRepository;
    @Autowired
    private ListingRepository listingRepository;

    private BookstoreService bookstoreService;

    @BeforeEach
    public void setUp() {
        bookstoreService = new BookstoreService(bookstoreRepository, listingRepository);
    }

    @Test
    public void testFindAll() {
        List<Bookstore> bookstores = bookstoreService.findAll();
        assertThat(bookstores).isEmpty();
    }

    @Test
    public void testCreateBookstore() {
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");

        bookstore = bookstoreService.createBookstore(bookstore);

        Optional<Bookstore> optionalBookstore = bookstoreRepository.findById(bookstore.getId());
        assertThat(optionalBookstore).isNotEmpty();
        assertEquals(bookstore.getbookstoreName(), optionalBookstore.get().getbookstoreName());
    }

    @Test
    public void testGetBookstoreById() {
        Bookstore bookstore = new Bookstore();
        bookstore.setbookstoreName("John's Bookstore");
        bookstore = bookstoreRepository.save(bookstore);

        Bookstore retrievedBookstore = bookstoreService.getBookstoreById(bookstore.getId());

        assertEquals(bookstore.getId(), retrievedBookstore.getId());
        assertEquals(bookstore.getbookstoreName(), retrievedBookstore.getbookstoreName());
    }

    @Test
    public void testGetBookstoreByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> bookstoreService.getBookstoreById(1L));
    }

}
