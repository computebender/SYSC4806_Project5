package ca.carleton.AmazinBookStore.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("johndoe@example.com");
        testUser.setPassword("password");
        testUser = userService.createUser(testUser);
    }

    @AfterEach
    public void cleanup() {
        userService.deleteUserById(testUser.getId());
    }

    @Test
    public void testCreateUser() {
        User newUser = new User();
        newUser.setFirstName("Jane");
        newUser.setLastName("Doe");
        newUser.setEmail("janedoe@example.com");
        newUser.setPassword("password");

        newUser = userService.createUser(newUser);

        assertNotNull(newUser.getId());
        assertEquals("Jane", newUser.getFirstName());
        assertEquals("Doe", newUser.getLastName());
        assertEquals("janedoe@example.com", newUser.getEmail());

        userService.deleteUserById(newUser.getId());
    }

    @Test
    public void testGetUserById() {
        User user = userService.getUserById(testUser.getId());

        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getLastName(), user.getLastName());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }

    @Test
    public void testGetUserByEmail() {
        User user = userService.getUserByEmail(testUser.getEmail());

        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getLastName(), user.getLastName());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
    }


    @Test
    public void testGetAllUsers() {
        User newUser1 = new User();
        newUser1.setFirstName("Jane");
        newUser1.setLastName("Doe");
        newUser1.setEmail("janedoe@example.com");
        newUser1.setPassword("password");

        User newUser2 = new User();
        newUser2.setFirstName("Bob");
        newUser2.setLastName("Smith");
        newUser2.setEmail("bobsmith@example.com");
        newUser2.setPassword("password");

        userService.createUser(newUser1);
        userService.createUser(newUser2);

        assertEquals(3, userService.getAllUsers().size());

        userService.deleteUserById(newUser1.getId());
        userService.deleteUserById(newUser2.getId());
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Doe");
        updatedUser.setEmail("janedoe@example.com");
        updatedUser.setPassword("newpassword");

        User user = userService.updateUser(testUser.getId(), updatedUser);

        assertEquals(testUser.getId(), user.getId());
        assertEquals(updatedUser.getFirstName(), user.getFirstName());
        assertEquals(updatedUser.getLastName(), user.getLastName());
        assertEquals(updatedUser.getEmail(), user.getEmail());
    }

    @Test
    public void testDeleteUserById() {
        User deleteUser = new User();
        deleteUser.setFirstName("Delete");
        deleteUser.setLastName("Me");
        deleteUser.setEmail("delete@example.com");
        deleteUser.setPassword("password");
        deleteUser = userService.createUser(deleteUser);

        userService.deleteUserById(deleteUser.getId());

        User finalDeleteUser = deleteUser;
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(finalDeleteUser.getId());
        });
    }
}
