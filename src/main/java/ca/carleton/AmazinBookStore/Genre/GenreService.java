package ca.carleton.AmazinBookStore.Genre;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll(){
        return this.genreRepository.findAll();
    }

    public Genre getGenreById(Long id){
        Optional<Genre> genre = this.genreRepository.findById(id);

        if(genre.isEmpty()){
            throw new ResourceNotFoundException("Genre with ID " + id + " not found.");
        }

        return genre.get();
    }

    public Genre createGenre(Genre genre){
        return this.genreRepository.save(genre);
    }

    public Genre updateGenre(Long id, Genre partialGenre) {
        Optional<Genre> optionalGenre = this.genreRepository.findById(id);
        if(optionalGenre.isEmpty()){
            throw new ResourceNotFoundException("Genre with ID " + id + " not found.");
        }
        Genre genre = optionalGenre.get();

        if(Objects.nonNull(partialGenre.getName())){
            genre.setName(partialGenre.getName());
        }

        this.genreRepository.save(genre);

        return genre;
    }

    public void deleteGenre(Long id) {
        Optional<Genre> optionalGenre = this.genreRepository.findById(id);
        if(optionalGenre.isEmpty()){
            throw new ResourceNotFoundException("Genre with ID " + id + " not found.");
        }
        this.genreRepository.deleteById(optionalGenre.get().getId());
    }


}
