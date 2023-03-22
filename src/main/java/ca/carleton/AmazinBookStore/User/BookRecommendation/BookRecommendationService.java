package ca.carleton.AmazinBookStore.User.BookRecommendation;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Book.BookRepository;
import ca.carleton.AmazinBookStore.User.User;
import ca.carleton.AmazinBookStore.User.UserController;
import ca.carleton.AmazinBookStore.User.UserRepository;
import ca.carleton.AmazinBookStore.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookRecommendationService {

    private final UserService userService;

    @Autowired
    public BookRecommendationService(UserService userService){
        this.userService = userService;
    }

    public BookRecommendation getRecommendationById(Long id){
        User user = userService.getUserById(id);
        List<User> users = userService.getAllUsers();
        BookRecommendation recommendation = new BookRecommendation();
        return recommendation.getRecommendationOfUser(user, users);
    }
}
