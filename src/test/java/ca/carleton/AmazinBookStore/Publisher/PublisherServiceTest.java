package ca.carleton.AmazinBookStore.Publisher;

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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AmazinBookStoreApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PublisherServiceTest {
    @Autowired
    private PublisherRepository publisherRepository;

    private PublisherService publisherService;

    @BeforeEach
    public void setUp() {
        publisherService = new PublisherService(publisherRepository);
    }

    @Test
    public void testFindAll() {
        List<Publisher> publishers = publisherService.findAll();
        assertThat(publishers).isEmpty();
    }

    @Test
    public void testCreatePublisher() {
        Publisher publisher = new Publisher();
        publisher.setFirstName("First");
        publisher.setLastName("Last");

        publisher = publisherService.createPublisher(publisher);

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getId());
        assertThat(optionalPublisher).isNotEmpty();
        assertEquals(publisher.getFirstName(), optionalPublisher.get().getFirstName());
        assertEquals(publisher.getLastName(), optionalPublisher.get().getLastName());
    }

    @Test
    public void testGetPublisherById() {
        Publisher publisher = new Publisher();
        publisher.setFirstName("First");
        publisher.setLastName("Last");
        publisher = publisherRepository.save(publisher);

        Publisher retrievedPublisher = publisherService.findPublisherById(publisher.getId());

        assertEquals(publisher.getId(), retrievedPublisher.getId());
        assertEquals(publisher.getFirstName(), retrievedPublisher.getFirstName());
        assertEquals(publisher.getLastName(), retrievedPublisher.getLastName());
    }

    @Test
    public void testGetPublisherByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> publisherService.findPublisherById(1L));
    }

    @Test
    public void testUpdatePublisher() {
        Publisher publisher = new Publisher();
        publisher.setFirstName("First");
        publisher.setLastName("Last");
        publisher = publisherRepository.save(publisher);

        Publisher partialPublisher = new Publisher();
        partialPublisher.setFirstName("Changed");

        Publisher updatedPublisher = publisherService.updatePublisher(publisher.getId(), partialPublisher);

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getId());
        assertThat(optionalPublisher).isNotEmpty();
        assertEquals(updatedPublisher.getFirstName(), optionalPublisher.get().getFirstName());
        assertEquals(publisher.getLastName(), optionalPublisher.get().getLastName());
    }

    @Test
    public void testDeletePublisher() {
        Publisher publisher = new Publisher();
        publisher.setFirstName("John");
        publisher.setLastName("Doe");
        publisher = publisherRepository.save(publisher);

        publisherService.deletePublisher(publisher.getId());

        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getId());
        assertThat(optionalPublisher).isEmpty();
    }

    @Test
    public void testDeletePublisherNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> publisherService.deletePublisher(1L));
    }
}
