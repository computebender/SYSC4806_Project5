package ca.carleton.AmazinBookStore.Publisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublisherTest {
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        publisher = new Publisher();
    }

    @Test
    public void testId() {
        publisher.setId(3L);
        assertEquals(3L, publisher.getId().longValue());
    }
    @Test
    public void testName() {
        publisher.setName("First");
        assertEquals("First", publisher.getName());
    }

    @Test
    public void testPublisherCreation() {
        assertNotNull(publisher);
    }

}
