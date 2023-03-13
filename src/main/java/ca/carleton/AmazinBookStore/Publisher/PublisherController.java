package ca.carleton.AmazinBookStore.Publisher;

import ca.carleton.AmazinBookStore.Author.Author;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;
    public PublisherController(PublisherService pS) {
        this.publisherService = pS;
    }

    @PostMapping()
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher){
        Publisher savedPublisher = this.publisherService.createPublisher(publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
    }

    @GetMapping()
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        List<Publisher> publishers = this.publisherService.findAll();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable long publisherId){
        Publisher publisher;
        try {
            publisher = this.publisherService.findPublisherById(publisherId);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publisher);
    }
    @PutMapping("/{publisherId}")
    public ResponseEntity<Publisher> updatePublisherById(@PathVariable long publisherId, @RequestBody Publisher publisherUpdated){
        Publisher publisher;
        try {
            publisher = publisherService.updatePublisher(publisherId, publisherUpdated);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publisher);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deletePublisherById(@PathVariable long publisherId){
        try {
            publisherService.deletePublisher(publisherId);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
