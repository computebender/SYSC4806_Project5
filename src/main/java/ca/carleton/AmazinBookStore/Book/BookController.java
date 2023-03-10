package ca.carleton.AmazinBookStore.Book;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Author.AuthorService;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import ca.carleton.AmazinBookStore.Publisher.PublisherService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final PublisherService publisherService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService, PublisherService publisherService){
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = this.bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        //get the author from the database
        Author author = authorService.getAuthorById(book.getAuthor().getId());
        //set the author of the book to the one gathered
        book.setAuthor(author);
        //get the publisher from the database
        Publisher publisher = publisherService.findPublisherById(book.getPublisher().getId());
        //set the publisher of the book to the one gathered
        book.setPublisher(publisher);
        //create the book
        bookService.createBook(book);
        //add the book to the list of books the author has
        author.getBooks().add(book);
        authorService.updateAuthor(author.getId(), author);
        //add the book to the list of books by the publisher
        publisher.getBooks().add(book);
        publisherService.updatePublisher(publisher.getId(), publisher);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable long bookId) {
        Book book;
        try {
            book = this.bookService.getBookById(bookId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBookById(@PathVariable long bookId, @RequestBody Book partialBook) {
        Book book;
        try {
            book = bookService.updateBook(bookId, partialBook);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();

    }



}

