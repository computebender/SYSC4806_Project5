package ca.carleton.AmazinBookStore.Publisher;

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
    public ResponseEntity<Publisher> createPublisher(){
        Publisher newPublisher = new Publisher();
        return ResponseEntity.status(HttpStatus.CREATED).body(newPublisher);
    }

    @GetMapping()
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        List<Publisher> publishers = this.publisherService.findAll();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable long publisherId){
        Optional<Publisher> publisher;
        publisher = this.publisherService.findPublisherById(publisherId);
        return ResponseEntity.ok(publisher.get());
    }
    @PutMapping("/{publisherId}")
    public ResponseEntity<Publisher> updatePublisherById(@PathVariable long publisherId, @RequestBody Publisher publisherUpdated){
        Publisher publisher;
        publisher = publisherService.updatePublisher(publisherId, publisherUpdated);
        return ResponseEntity.ok(publisher);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Publisher> deletePublisherById(@PathVariable long publisherId, @RequestBody Publisher publisherUpdated){
        Publisher publisher;
        publisher = publisherService.deletePublisher(publisherId);
        return ResponseEntity.ok(publisher);
    }

}
