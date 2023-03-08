package ca.carleton.AmazinBookStore.Book;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Author.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> findAll(){
        return this.bookRepository.findAll();
    }

    public Book getBookById(Long id){
        Optional<Book> book = this.bookRepository.findById(id);

        if(book.isEmpty()){
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }

        return book.get();
    }

    @Transactional
    public Book createBook(Book book){
        Author author = book.getAuthor();
        author.getBooks().add(book);
        this.authorRepository.save(author);
        return this.bookRepository.save(book);
    }

    public Book updateBook(Long id, Book partialBook) {
        Optional<Book> optionalBook = this.bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }
        Book book = optionalBook.get();

        if(Objects.nonNull(partialBook.getIsbn())){
            book.setIsbn(partialBook.getIsbn());
        }

        if(Objects.nonNull(partialBook.getPicture())){
            book.setPicture(partialBook.getPicture());
        }


        if(Objects.nonNull(partialBook.getDescription())){
            book.setDescription(partialBook.getDescription());
        }

        if(Objects.nonNull(partialBook.getAuthor())){
            book.setAuthor(partialBook.getAuthor());
        }

        if(Objects.nonNull(partialBook.getTitle())){
            book.setTitle(partialBook.getTitle());
        }

        this.bookRepository.save(book);

        return book;
    }

    public void deleteBook(Long id) {
        Optional<Book> optionalBook = this.bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            throw new ResourceNotFoundException("Book with ID " + id + " not found.");
        }
        this.bookRepository.deleteById(optionalBook.get().getId());
    }

}
