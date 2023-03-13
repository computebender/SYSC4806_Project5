package ca.carleton.AmazinBookStore.ShoppingCart;

import ca.carleton.AmazinBookStore.Book.BookRepository;
import ca.carleton.AmazinBookStore.Publisher.PublisherService;
import org.apache.coyote.Response;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService){
        this.shoppingCartService = shoppingCartService;
    }
    @PostMapping("/{userId}")
    public ResponseEntity<ShoppingCart> createCart(@PathVariable String userId) {
        List<CartItem> items = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart(userId, items);
        ShoppingCart shoppingCart1 = this.shoppingCartService.createShoppingCart(shoppingCart);
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCart1);
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = this.shoppingCartService.findAll();
        return ResponseEntity.ok(shoppingCarts);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable String userId) {
        ShoppingCart shoppingCart;
        try{
            shoppingCart = this.shoppingCartService.findShoppingCartByUserId(userId);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/{userId}/add-item/")
    public ResponseEntity<ShoppingCart> addItem(@PathVariable String userId, @RequestBody CartItem item) {
        ShoppingCart cart = this.shoppingCartService.addShoppingCartItem(item,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @DeleteMapping("/{userId}/remove-item/")
    public ResponseEntity<ShoppingCart> removeItem(@PathVariable String userId, @RequestBody CartItem removeCartItem) {
       try {
           ShoppingCart cart = this.shoppingCartService.removeShoppingCartItem(removeCartItem, userId);
       }catch(ResourceNotFoundException e){
           return ResponseEntity.notFound().build();
       }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/update-item/{bookId}")
    public ResponseEntity<ShoppingCart> updateItem(@PathVariable String userId, @PathVariable Long bookId, @RequestBody CartItem updatedItem) {
        ShoppingCart cart = this.shoppingCartService.updateShoppingCart(userId, bookId, updatedItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PostMapping("/{userId}/clear/")
    public ResponseEntity<ShoppingCart> clearCart(@PathVariable String userId) {
        ShoppingCart cart = this.shoppingCartService.clearShoppingCart(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

//    @GetMapping("/checkout")
//    public ShoppingCart getCartForCheckout(@RequestParam("userId") String userId) {
//        ShoppingCart cart = this.shoppingCartService.findShoppingCartByUserId(userId);
//
//        return carts.get(userId);
//    }
//
//    @PostMapping("/checkout")
//    public void checkout(@RequestParam("userId") String userId) {
//        ShoppingCart cart = this.shoppingCartService.findShoppingCartByUserId(userId);
//
//        // Simulate payment processing here
//        carts.remove(userId);
//    }
}
