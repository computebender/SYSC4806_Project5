package ca.carleton.AmazinBookStore.Book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);

        Book result = bookService.getBookById(1L);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    public void testCreateBook() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.createBook(book);

        Assertions.assertEquals(book, result);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(anyLong())).thenReturn(optionalBook);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.updateBook(1L, book);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    public void testDeleteBook() {
        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
    }

}
