package ca.carleton.AmazinBookStore.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll(){
        return this.authorRepository.findAll();
    }

    public Author getAuthorById(Long id){
        return this.authorRepository.getReferenceById(id);
    }

    public Author createAuthor(Author author){
        return this.authorRepository.save(author);
    }
}
