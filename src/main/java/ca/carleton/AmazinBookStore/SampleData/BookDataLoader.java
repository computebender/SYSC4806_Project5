package ca.carleton.AmazinBookStore.SampleData;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import ca.carleton.AmazinBookStore.Author.AuthorRepository;
import ca.carleton.AmazinBookStore.Book.BookRepository;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Genre.GenreRepository;
import ca.carleton.AmazinBookStore.Publisher.Publisher;
import ca.carleton.AmazinBookStore.Publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Order(1)
public class BookDataLoader implements CommandLineRunner {
    @Autowired
    private Environment environment;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genreRepository;

    public BookDataLoader(AuthorRepository authorRepository,
                          BookRepository bookRepository,
                          PublisherRepository publisherRepository,
                          GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean enabled = environment.getProperty("amazinbookstore.sampledata.enabled", Boolean.class, false);
        if (enabled) {
            generateData();
        }
    }

    @Transactional
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

        Author author5 = new Author();
        author5.setFirstName("J.R.R.");
        author5.setLastName("Tolkien");
        authorRepository.save(author5);


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

        // create genres
        Genre mystery = new Genre();
        mystery.setName("Mystery");
        genreRepository.save(mystery);

        Genre fantasy = new Genre();
        fantasy.setName("Fantasy");
        genreRepository.save(fantasy);

        Genre horror = new Genre();
        horror.setName("Horror");
        genreRepository.save(horror);

        Genre dystopian = new Genre();
        dystopian.setName("Dystopian");
        genreRepository.save(dystopian);

        Genre historical = new Genre();
        historical.setName("Historical");
        genreRepository.save(historical);

        Genre scifi = new Genre();
        scifi.setName("Science Fiction");
        genreRepository.save(scifi);

        // create books and associate with authors and publishers
        Book book1 = new Book();
        book1.setTitle("Murder on the Orient Express");
        book1.setIsbn("9780006170068");
        book1.setPicture("https://upload.wikimedia.org/wikipedia/en/c/c0/Murder_on_the_Orient_Express_First_Edition_Cover_1934.jpg");
        book1.setDescription("A Hercule Poirot Mystery");
        book1.setAuthor(author1);
        book1.setPublisher(publisher1);
        book1.setGenres(List.of(mystery));
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Harry Potter and the Philosopher's Stone");
        book2.setIsbn("9780439362139");
        book2.setPicture("https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg");
        book2.setDescription("The first book in the Harry Potter series");
        book2.setAuthor(author2);
        book2.setPublisher(publisher2);
        book2.setGenres(List.of(fantasy));
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("The Shining");
        book3.setIsbn("9780385121675");
        book3.setPicture("https://upload.wikimedia.org/wikipedia/en/4/4c/Shiningnovel.jpg");
        book3.setDescription("A horror novel by Stephen King");
        book3.setAuthor(author3);
        book3.setPublisher(publisher3);
        book3.setGenres(List.of(horror));
        bookRepository.save(book3);

        Book book4 = new Book();
        book4.setTitle("The Handmaid's Tale");
        book4.setIsbn("9780385539241");
        book4.setPicture("https://upload.wikimedia.org/wikipedia/en/1/18/TheHandmaidsTale%281stEd%29.jpg");
        book4.setDescription("A dystopian novel by Margaret Atwood");
        book4.setAuthor(author4);
        book4.setPublisher(publisher3);
        book4.setGenres(List.of(dystopian));
        bookRepository.save(book4);

        Book book5 = new Book();
        book5.setTitle("Carrie");
        book5.setIsbn("9780307348074");
        book5.setPicture("https://upload.wikimedia.org/wikipedia/en/3/31/Carrienovel.jpg");
        book5.setDescription("A horror novel by Stephen King");
        book5.setAuthor(author3);
        book5.setPublisher(publisher4);
        book5.setGenres(List.of(horror));
        bookRepository.save(book5);

        Book book6 = new Book();
        book6.setTitle("The Stand");
        book6.setIsbn("9780385121682");
        book6.setPicture("https://upload.wikimedia.org/wikipedia/en/9/96/The_Stand_cover.jpg");
        book6.setDescription("A post-apocalyptic horror novel by Stephen King");
        book6.setAuthor(author3);
        book6.setPublisher(publisher3);
        book6.setGenres(List.of(horror, scifi));
        bookRepository.save(book6);

        Book book7 = new Book();
        book7.setTitle("Alias Grace");
        book7.setIsbn("9780385490443");
        book7.setPicture("https://upload.wikimedia.org/wikipedia/en/e/e1/AliasGrace.jpg");
        book7.setDescription("A historical fiction novel by Margaret Atwood");
        book7.setAuthor(author4);
        book7.setPublisher(publisher3);
        book7.setGenres(List.of(historical));
        bookRepository.save(book7);

        Book book8 = new Book();
        book8.setTitle("The Dark Tower I: The Gunslinger");
        book8.setIsbn("9780452279605");
        book8.setPicture("https://upload.wikimedia.org/wikipedia/en/d/db/The_Gunslinger.jpg");
        book8.setDescription("A dark fantasy novel by Stephen King");
        book8.setAuthor(author3);
        book8.setPublisher(publisher4);
        book8.setGenres(List.of(fantasy));
        bookRepository.save(book8);

        Book book9 = new Book();
        book9.setTitle("Oryx and Crake");
        book9.setIsbn("9780385503853");
        book9.setPicture("https://upload.wikimedia.org/wikipedia/en/c/cd/OryxAndCrake.jpg");
        book9.setDescription("A science fiction novel by Margaret Atwood");
        book9.setAuthor(author4);
        book9.setPublisher(publisher4);
        book9.setGenres(List.of(scifi));
        bookRepository.save(book9);

        Book book10 = new Book();
        book10.setTitle("The Lord of the Rings");
        book10.setIsbn("9780261103689");
        book10.setPicture("https://upload.wikimedia.org/wikipedia/en/e/e9/First_Single_Volume_Edition_of_The_Lord_of_the_Rings.gif");
        book10.setDescription("An epic high fantasy novel");
        book10.setAuthor(author5);
        book10.setPublisher(publisher1);
        book10.setGenres(List.of(fantasy));
        bookRepository.save(book10);
    }
}
