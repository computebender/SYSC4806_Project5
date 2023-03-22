package ca.carleton.AmazinBookStore.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Author.AuthorRepository;
import ca.carleton.AmazinBookStore.Author.AuthorService;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Book.BookRepository;
import ca.carleton.AmazinBookStore.Book.BookService;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreRepository;
import ca.carleton.AmazinBookStore.Bookstore.BookstoreService;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Genre.GenreRepository;
import ca.carleton.AmazinBookStore.Genre.GenreService;
import ca.carleton.AmazinBookStore.Listing.ListingRepository;
import ca.carleton.AmazinBookStore.Listing.ListingService;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import ca.carleton.AmazinBookStore.Publisher.PublisherRepository;
import ca.carleton.AmazinBookStore.Publisher.PublisherService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import ca.carleton.AmazinBookStore.Listing.Listing;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AmazinBookStoreApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ShoppingCartWebTest {
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private BookstoreRepository bookstoreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private GenreRepository genreRepository;
    private BookstoreService bookstoreService;

    private ListingService listingService;
    private BookService bookService;

    private AuthorService authorService;

    private PublisherService publisherService;

    private GenreService genreService;
    @BeforeEach
    void setUp() {
        this.shoppingCartService = new ShoppingCartService(cartRepository, itemRepository);
        this.bookstoreService = new BookstoreService(bookstoreRepository,listingRepository);
        this.listingService = new ListingService(listingRepository);
        this.bookService = new BookService(bookRepository);
        this.authorService = new AuthorService(authorRepository);
        this.publisherService = new PublisherService(publisherRepository);
        this.genreService = new GenreService(genreRepository);
    }
}
