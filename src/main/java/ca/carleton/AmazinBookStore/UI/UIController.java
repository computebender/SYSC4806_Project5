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
    public String author(){
        return "author";
    }
}
