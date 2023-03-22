package ca.carleton.AmazinBookStore.Publisher;

import ca.carleton.AmazinBookStore.Author.Author;
import ca.carleton.AmazinBookStore.Book.Book;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher createPublisher(Publisher publisher){
        return this.publisherRepository.save(publisher);
    }

    public List<Publisher> findAll(){
        return this.publisherRepository.findAll();
    }

    public List<Book> getPublisherBookById(long publisherId){
        Optional<Publisher> publisher = this.publisherRepository.findById(publisherId);

        if(publisher.isEmpty()){
            throw new ResourceNotFoundException("Publisher with ID " + publisherId + " not found.");
        }

        return publisher.get().getBooks();
    }

    public Publisher findPublisherById(long publisherId){
        Optional<Publisher> publisher = this.publisherRepository.findById(publisherId);

        if(publisher.isEmpty()){
            throw new ResourceNotFoundException("Publisher with ID " + publisherId + " not found.");
        }

        return publisher.get();
    }

    public Publisher updatePublisher(Long publisherId, Publisher updatedPublisher){
        Optional<Publisher> oldPublisher = this.publisherRepository.findById(publisherId);
        if(oldPublisher.isEmpty()){
            throw new ResourceNotFoundException("Publisher with ID " + publisherId + " not found.");
        }
        Publisher publisher = oldPublisher.get();

        if(Objects.nonNull(updatedPublisher.getName())){
            publisher.setName(updatedPublisher.getName());
        }
        this.publisherRepository.save(publisher);

        return publisher;
    }

    public Publisher deletePublisher(Long id) {
        Optional<Publisher> optionalPublisher = this.publisherRepository.findById(id);
        if(optionalPublisher.isEmpty()){
            throw new ResourceNotFoundException("Publisher with ID " + id + " not found.");
        }
        this.publisherRepository.deleteById(optionalPublisher.get().getId());

        return optionalPublisher.get();
    }

}
