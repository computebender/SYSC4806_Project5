package ca.carleton.AmazinBookStore.Genres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Genre.Genre;
import ca.carleton.AmazinBookStore.Genre.GenreRepository;
import ca.carleton.AmazinBookStore.Genre.GenreService;
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
public class GenresServiceTest {

    @Autowired
    private GenreRepository genreRepository;

    private GenreService genreService;

    @BeforeEach
    public void setUp() {
        genreService = new GenreService(genreRepository);
    }

    @Test
    public void testFindAll() {
        List<Genre> genres = genreService.findAll();
        assertThat(genres).isEmpty();
    }

    @Test
    public void testCreateGenre() {
        Genre genre = new Genre();
        genre.setName("Fiction");

        genre = genreService.createGenre(genre);

        Optional<Genre> optionalGenre = genreRepository.findById(genre.getId());
        assertThat(optionalGenre).isNotEmpty();
        assertEquals(genre.getName(), optionalGenre.get().getName());
    }

    @Test
    public void testGetGenreById() {
        Genre genre = new Genre();
        genre.setName("Fiction");
        genre = genreRepository.save(genre);

        Genre retrievedGenre = genreService.getGenreById(genre.getId());

        assertEquals(genre.getId(), retrievedGenre.getId());
        assertEquals(genre.getName(), retrievedGenre.getName());
    }

    @Test
    public void testGetGenreBooksById(){
        Genre genre = new Genre();
        genre.setName("Fiction");
        List<Book> books = new ArrayList<>();
        Book b1 = new Book();
        b1.setTitle("Test");
        books.add(b1);
        genre.setBooks(books);
        genre = genreRepository.save(genre);

        List<Book> genreBooks = genreService.getGenreBooksById(genre.getId());
        assertEquals(b1.getTitle(), genreBooks.get(0).getTitle());
    }

    @Test
    public void testGetGenreByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> genreService.getGenreById(1L));
    }

    @Test
    public void testUpdateGenre() {
        Genre genre = new Genre();
        genre.setName("Fiction");
        genre = genreRepository.save(genre);

        Genre partialGenre = new Genre();
        partialGenre.setName("Non-Fiction");

        Genre updatedGenre = genreService.updateGenre(genre.getId(), partialGenre);

        Optional<Genre> optionalGenre = genreRepository.findById(genre.getId());
        assertThat(optionalGenre).isNotEmpty();
        assertEquals(updatedGenre.getName(), optionalGenre.get().getName());
    }

    @Test
    public void testDeleteGenre() {
        Genre genre = new Genre();
        genre.setName("Fiction");
        genre = genreRepository.save(genre);

        genreService.deleteGenre(genre.getId());

        Optional<Genre> optionalGenre = genreRepository.findById(genre.getId());
        assertThat(optionalGenre).isEmpty();
    }

    @Test
    public void testDeleteGenreNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> genreService.deleteGenre(1L));
    }
}
