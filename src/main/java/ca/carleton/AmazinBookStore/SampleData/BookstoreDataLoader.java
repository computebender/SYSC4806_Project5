package ca.carleton.AmazinBookStore.SampleData;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Book.BookRepository;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreRepository;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class BookstoreDataLoader implements CommandLineRunner {
    @Autowired
    private Environment environment;
    private final BookRepository bookRepository;
    private final BookstoreRepository bookstoreRepository;
    private final ListingRepository listingRepository;

    public BookstoreDataLoader(BookRepository bookRepository,
                               BookstoreRepository bookstoreRepository,
                               ListingRepository listingRepository) {
        this.bookRepository = bookRepository;
        this.bookstoreRepository = bookstoreRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean enabled = environment.getProperty("amazinbookstore.sampledata.enabled", Boolean.class, false);
        if (enabled) {
            generateBookstores();
        }
    }

    void generateBookstores() {
        // create bookstores
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setbookstoreName("Fiction World");
        bookstoreRepository.save(bookstore1);

        Bookstore bookstore2 = new Bookstore();
        bookstore2.setbookstoreName("Adventure Hub");
        bookstoreRepository.save(bookstore2);

        // fetch all books
        List<Book> books = bookRepository.findAll();

        // create listings for each book in both bookstores
        for (Book book : books) {
            Listing listing1 = new Listing(bookstore1, 19.99, 5, book);
            Listing listing2 = new Listing(bookstore2, 21.99, 3, book);

            bookstore1.addListing(listing1);
            bookstore2.addListing(listing2);

            listingRepository.save(listing1);
            listingRepository.save(listing2);
        }

        // save updated bookstores
        bookstoreRepository.save(bookstore1);
        bookstoreRepository.save(bookstore2);
    }
}