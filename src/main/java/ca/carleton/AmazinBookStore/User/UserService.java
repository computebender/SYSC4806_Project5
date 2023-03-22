package ca.carleton.AmazinBookStore.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        return user.get();
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User partialUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        User user = optionalUser.get();

        if (Objects.nonNull(partialUser.getEmail())) {
            user.setEmail(partialUser.getEmail());
        }

        if (Objects.nonNull(partialUser.getPassword())) {
            user.setPassword(partialUser.getPassword());
        }

        if (Objects.nonNull(partialUser.getFirstName())) {
            user.setFirstName(partialUser.getFirstName());
        }

        if (Objects.nonNull(partialUser.getLastName())) {
            user.setLastName(partialUser.getLastName());
        }

        userRepository.save(user);

        return user;
    }

    public void deleteUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
    }
}

