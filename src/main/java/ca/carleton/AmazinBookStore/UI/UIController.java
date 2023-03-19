package ca.carleton.AmazinBookStore.UI;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class UIController {
    @GetMapping("/")
    public String serveUI(Model model){
        model.addAttribute("message", "Success!!");
        return "landing";
    }

    @GetMapping("/author")
    public String author() {
        return "author";
    }

    @GetMapping("/genre")
    public String genre() {
        return "genre";
    }

    @GetMapping("/login")
    public String loginUI(){
        return "login";
    }

    @GetMapping("/bookstore")
    public String bookstoreUI(){
        return "bookstore";
    }

    @GetMapping("/profile")
    public String profileUI(){
        return "profile";
    }

    @GetMapping("/listing")
    public String listingUI(){
        return "listing";
    }

    @GetMapping("/cart")
    public String cartUI(){
        return "cart";
    }

    @GetMapping("/publisher")
    public String publisherUI(){
        return "publisher";
    }

    @GetMapping("/book")
    public String bookUI(){
        return "book";
    }
}
