package ca.carleton.AmazinBookStore.Author;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Author> author = this.authorRepository.findById(id);

        if(author.isEmpty()){
            throw new ResourceNotFoundException("Author with ID " + id + " not found.");
        }

        return author.get();
    }

    public Author createAuthor(Author author){
        return this.authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author partialAuthor) {
        Optional<Author> optionalAuthor = this.authorRepository.findById(id);
        if(optionalAuthor.isEmpty()){
            throw new ResourceNotFoundException("Author with ID " + id + " not found.");
        }
        Author author = optionalAuthor.get();

        if(Objects.nonNull(partialAuthor.getFirstName())){
            author.setFirstName(partialAuthor.getFirstName());
        }

        if(Objects.nonNull(partialAuthor.getLastName())){
            author.setFirstName(partialAuthor.getLastName());
        }

        this.authorRepository.save(author);

        return author;
    }

    public void deleteAuthor(Long id) {
        Optional<Author> optionalAuthor = this.authorRepository.findById(id);
        if(optionalAuthor.isEmpty()){
            throw new ResourceNotFoundException("Author with ID " + id + " not found.");
        }
        this.authorRepository.deleteById(optionalAuthor.get().getId());
    }


}
