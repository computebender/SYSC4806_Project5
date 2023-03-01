package ca.carleton.AmazinBookStore.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import ca.carleton.AmazinBookStore.AmazinBookStoreApplication;
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
public class AuthorServiceTest {

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        authorService = new AuthorService(authorRepository);
    }

    @Test
    public void testFindAll() {
        List<Author> authors = authorService.findAll();
        assertThat(authors).isEmpty();
    }

    @Test
    public void testCreateAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        author = authorService.createAuthor(author);

        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        assertThat(optionalAuthor).isNotEmpty();
        assertEquals(author.getFirstName(), optionalAuthor.get().getFirstName());
        assertEquals(author.getLastName(), optionalAuthor.get().getLastName());
    }

    @Test
    public void testGetAuthorById() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author = authorRepository.save(author);

        Author retrievedAuthor = authorService.getAuthorById(author.getId());

        assertEquals(author.getId(), retrievedAuthor.getId());
        assertEquals(author.getFirstName(), retrievedAuthor.getFirstName());
        assertEquals(author.getLastName(), retrievedAuthor.getLastName());
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));
    }

    @Test
    public void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author = authorRepository.save(author);

        Author partialAuthor = new Author();
        partialAuthor.setFirstName("Jane");

        Author updatedAuthor = authorService.updateAuthor(author.getId(), partialAuthor);

        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        assertThat(optionalAuthor).isNotEmpty();
        assertEquals(updatedAuthor.getFirstName(), optionalAuthor.get().getFirstName());
        assertEquals(author.getLastName(), optionalAuthor.get().getLastName());
    }

    @Test
    public void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author = authorRepository.save(author);

        authorService.deleteAuthor(author.getId());

        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        assertThat(optionalAuthor).isEmpty();
    }

    @Test
    public void testDeleteAuthorNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(1L));
    }
}
