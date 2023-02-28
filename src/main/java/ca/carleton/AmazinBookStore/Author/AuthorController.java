package ca.carleton.AmazinBookStore.Author;

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

}
