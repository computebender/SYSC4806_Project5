package ca.carleton.AmazinBookStore.Author;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping()
    public ResponseEntity<List<Author>> getAllAuthors(){
        List<Author> authors = this.authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @PostMapping()
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        Author savedAuthor = this.authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable long authorId) {
        Author author;
        try {
            author = this.authorService.getAuthorById(authorId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(author);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthorById(@PathVariable long authorId, @RequestBody Author partialAuthor) {
        Author author;
        try {
            author = authorService.updateAuthor(authorId, partialAuthor);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        try {
            authorService.deleteAuthor(authorId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();

    }



}
