package ca.carleton.AmazinBookStore.Genre;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService){
        this.genreService = genreService;
    }

    @GetMapping()
    public ResponseEntity<List<Genre>> getAllGenres(){
        List<Genre> genres = this.genreService.findAll();
        return ResponseEntity.ok(genres);
    }

    @PostMapping()
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre){
        Genre savedGenre = this.genreService.createGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Genre> getGenreById(@PathVariable long genreId) {
        Genre genre;
        try {
            genre = this.genreService.getGenreById(genreId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(genre);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<Genre> updateGenreById(@PathVariable long genreId, @RequestBody Genre partialGenre) {
        Genre genre;
        try {
            genre = genreService.updateGenre(genreId, partialGenre);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long genreId) {
        try {
            genreService.deleteGenre(genreId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();

    }



}
