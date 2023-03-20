package ca.carleton.AmazinBookStore;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import ca.carleton.AmazinBookStore.Author.AuthorRepository;
import ca.carleton.AmazinBookStore.Book.BookRepository;
import ca.carleton.AmazinBookStore.Publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {
    @Autowired
    private Environment environment;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public SampleDataLoader(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean enabled = environment.getProperty("amazinbookstore.sampledata.enabled", Boolean.class, false);
        if (enabled) {
            generateData();
        }
    }

    void generateData() {
        // create authors
        Author author1 = new Author();
        author1.setFirstName("Agatha");
        author1.setLastName("Christie");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setFirstName("J.K.");
        author2.setLastName("Rowling");
        authorRepository.save(author2);

        Author author3 = new Author();
        author3.setFirstName("Stephen");
        author3.setLastName("King");
        authorRepository.save(author3);

        Author author4 = new Author();
        author4.setFirstName("Margaret");
        author4.setLastName("Atwood");
        authorRepository.save(author4);

        // create publishers
        Publisher publisher1 = new Publisher();
        publisher1.setName("HarperCollins");
        publisherRepository.save(publisher1);

        Publisher publisher2 = new Publisher();
        publisher2.setName("Scholastic");
        publisherRepository.save(publisher2);

        Publisher publisher3 = new Publisher();
        publisher3.setName("Penguin Random House");
        publisherRepository.save(publisher3);

        Publisher publisher4 = new Publisher();
        publisher4.setName("Simon & Schuster");
        publisherRepository.save(publisher4);

        // create books and associate with authors and publishers
        Book book1 = new Book();
        book1.setTitle("Murder on the Orient Express");
        book1.setIsbn("9780007");
        book1.setPicture("");
        book1.setDescription("A Hercule Poirot Mystery");
        book1.setAuthor(author1);
        book1.setPublisher(publisher1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Harry Potter and the Philosopher's Stone");
        book2.setIsbn("978074");
        book2.setPicture("");
        book2.setDescription("The first book in the Harry Potter series");
        book2.setAuthor(author2);
        book2.setPublisher(publisher2);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("The Shining");
        book3.setIsbn("978038");
        book3.setPicture("");
        book3.setDescription("A horror novel by Stephen King");
        book3.setAuthor(author3);
        book3.setPublisher(publisher3);
        bookRepository.save(book3);

        Book book4 = new Book();
        book4.setTitle("The Handmaid's Tale");
        book4.setIsbn("978038");
        book4.setPicture("");
        book4.setDescription("A dystopian novel by Margaret Atwood");
        book4.setAuthor(author4);
        book4.setPublisher(publisher3);
        bookRepository.save(book4);

        Book book5 = new Book();
        book5.setTitle("Carrie");
        book5.setIsbn("9780307");
        book5.setPicture("");
        book5.setDescription("A horror novel by Stephen King");
        book5.setAuthor(author3);
        book5.setPublisher(publisher4);
        bookRepository.save(book5);

        Book book6 = new Book();
        book6.setTitle("The Stand");
        book6.setIsbn("978038");
        book6.setPicture("");
        book6.setDescription("A post-apocalyptic horror novel by Stephen King");
        book6.setAuthor(author3);
        book6.setPublisher(publisher3);
        bookRepository.save(book6);

        Book book7 = new Book();
        book7.setTitle("Alias Grace");
        book7.setIsbn("978038");
        book7.setPicture("");
        book7.setDescription("A historical fiction novel by Margaret Atwood");
        book7.setAuthor(author4);
        book7.setPublisher(publisher3);
        bookRepository.save(book7);

        Book book8 = new Book();
        book8.setTitle("The Dark Tower I: The Gunslinger");
        book8.setIsbn("9780451");
        book8.setPicture("");
        book8.setDescription("A dark fantasy novel by Stephen King");
        book8.setAuthor(author3);
        book8.setPublisher(publisher4);
        bookRepository.save(book8);

        Book book9 = new Book();
        book9.setTitle("Oryx and Crake");
        book9.setIsbn("9780385");
        book9.setPicture("");
        book9.setDescription("A science fiction novel by Margaret Atwood");
        book9.setAuthor(author4);
        book9.setPublisher(publisher4);
        bookRepository.save(book9);

        Book book10 = new Book();
        book10.setTitle("The Lord of the Rings");
        book10.setIsbn("9780261");
        book10.setPicture("");
        book10.setDescription("An epic high fantasy novel");
        book10.setAuthor(author1);
        book10.setPublisher(publisher1);
        bookRepository.save(book10);
    }
}
