package ca.carleton.AmazinBookStore.SampleData;

import ca.carleton.AmazinBookStore.User.User;
import ca.carleton.AmazinBookStore.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    private Environment environment;
    private final UserService userService;

    public UserDataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean enabled = environment.getProperty("amazinbookstore.sampledata.enabled", Boolean.class, false);
        if (enabled) {
            generateUsers();
        }
    }

    void generateUsers() {
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password1");
        userService.createUser(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPassword("password2");
        userService.createUser(user2);

        User user3 = new User();
        user3.setFirstName("Alice");
        user3.setLastName("Johnson");
        user3.setEmail("alice.johnson@example.com");
        user3.setPassword("password3");
        userService.createUser(user3);
    }
}
