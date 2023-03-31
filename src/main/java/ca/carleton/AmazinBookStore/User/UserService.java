package ca.carleton.AmazinBookStore.User;

import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.ShoppingCart.CartRepository;
import ca.carleton.AmazinBookStore.ShoppingCart.ShoppingCart;
import ca.carleton.AmazinBookStore.ShoppingCart.ShoppingCartService;
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
    private final CartRepository cartRepository;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        ShoppingCart shoppingCart = new ShoppingCart();
        ShoppingCart savedShoppingCart = cartRepository.save(shoppingCart);
        user.setShoppingCart(savedShoppingCart);
        User savedUser = userRepository.save(user);
        savedShoppingCart.setUser(savedUser);
        shoppingCart.setUser(savedUser);
        cartRepository.save(shoppingCart);
        return savedUser;
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
            String hashedPassword = passwordEncoder.encode(partialUser.getPassword());
            user.setPassword(hashedPassword);
        }

        if (Objects.nonNull(partialUser.getFirstName())) {
            user.setFirstName(partialUser.getFirstName());
        }

        if (Objects.nonNull(partialUser.getLastName())) {
            user.setLastName(partialUser.getLastName());
        }

        if(Objects.nonNull(partialUser.getPurchaseHistory())){
            user.setPurchaseHistory(partialUser.getPurchaseHistory());
        }

        userRepository.save(user);

        return user;
    }

    public User updatePurchaseHistory(Long id, List<Listing> purchaseHistory){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        User user = optionalUser.get();
        user.expandPurchaseHistory(purchaseHistory);
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    public void deleteUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        User user = optionalUser.get();
        Optional<ShoppingCart> sc = this.cartRepository.findById(user.getShoppingCart().getId());
        ShoppingCart savedSc = sc.get();
        savedSc.setUser(null);
        cartRepository.save(savedSc);
        user.setShoppingCart(null);
        userRepository.save(user);

        userRepository.deleteById(id);
        cartRepository.deleteById(savedSc.getId());
    }
}

