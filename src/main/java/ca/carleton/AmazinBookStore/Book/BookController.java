package ca.carleton.AmazinBookStore.Book;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = this.bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        System.out.println(book.getAuthor());
        System.out.println(book.getPublisher());
        Book savedBook = this.bookService.createBook(book);
        System.out.println(savedBook.getAuthor());
        System.out.println(savedBook.getPublisher());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
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

