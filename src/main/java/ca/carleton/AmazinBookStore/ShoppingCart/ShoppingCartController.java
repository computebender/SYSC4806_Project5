    package ca.carleton.AmazinBookStore.ShoppingCart;

    import ca.carleton.AmazinBookStore.Book.BookRepository;
    import ca.carleton.AmazinBookStore.Listing.Listing;
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
        @PostMapping("/{id}")
        public ResponseEntity<ShoppingCart> createCart(@PathVariable Long id) {
            List<CartItem> items = new ArrayList<>();
            ShoppingCart shoppingCart = new ShoppingCart();
            ShoppingCart shoppingCart1 = this.shoppingCartService.createShoppingCart(shoppingCart);
            return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCart1);
        }

        @GetMapping
        public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
            List<ShoppingCart> shoppingCarts = this.shoppingCartService.findAll();
            return ResponseEntity.ok(shoppingCarts);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
            ShoppingCart shoppingCart;
            try{
                shoppingCart = this.shoppingCartService.findShoppingCartById(id);
            }catch (ResourceNotFoundException e){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(shoppingCart);
        }

        @PostMapping("/{id}/add-item")
        public ResponseEntity<ShoppingCart> addItem(@PathVariable Long id, @RequestBody Listing listing) {
            ShoppingCart cart = this.shoppingCartService.addShoppingCartItem(listing,id);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        }

        @DeleteMapping("/{id}/remove-item")
        public ResponseEntity<Void> removeItem(@PathVariable Long id, @RequestBody Long cartItemId) {
           try {
               this.shoppingCartService.removeShoppingCartItem(cartItemId, id);
           }catch(ResourceNotFoundException e){
               return ResponseEntity.notFound().build();
           }
            return ResponseEntity.noContent().build();
        }

        @PostMapping("/{id}/update-item/{bookId}")
        public ResponseEntity<ShoppingCart> updateItem(@PathVariable Long id, @PathVariable Long bookId, @RequestBody CartItem updatedItem) {
            ShoppingCart cart = this.shoppingCartService.updateShoppingCart(id, bookId, updatedItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        }

        @PostMapping("/{id}/clear")
        public ResponseEntity<ShoppingCart> clearCart(@PathVariable Long id) {
            ShoppingCart cart = this.shoppingCartService.clearShoppingCart(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        }

        @PostMapping("/{id}/checkout")
        public ResponseEntity<Double> checkout(@PathVariable Long id) {
            double price;
            try {
                price = this.shoppingCartService.checkoutShoppingCart(id);
            }catch (Exception e){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(price);
        }
    }
