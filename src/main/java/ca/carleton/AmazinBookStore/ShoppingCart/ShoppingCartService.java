package ca.carleton.AmazinBookStore.ShoppingCart;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class ShoppingCartService {

    private final CartRepository cartRepository;

    public ShoppingCartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        List<ShoppingCart> shoppingCarts = this.findAll();
        for(ShoppingCart shoppingCart1 : shoppingCarts){
            if(shoppingCart1.getUserId().equals(shoppingCart.getUserId())){
                throw new DuplicateKeyException("ShoppingCart with user ID " + shoppingCart.getUserId() + " already exists.");
            }
        }

        return this.cartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> findAll() {
        return this.cartRepository.findAll();
    }

    public ShoppingCart findShoppingCartByUserId(String userId){
        Optional<ShoppingCart> shoppingCart = this.cartRepository.findByUserId(userId);
        if(shoppingCart.isEmpty()){
            throw new ResourceNotFoundException("ShoppingCart with user ID " + userId + " not found.");
        }

        return shoppingCart.get();
    }

    public ShoppingCart addShoppingCartItem(CartItem cartItem, String userId){
        ShoppingCart shoppingCart = this.findShoppingCartByUserId(userId);
        shoppingCart.addItem(cartItem);
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart removeShoppingCartItem(CartItem cartItem, String userId){
        ShoppingCart shoppingCart = this.findShoppingCartByUserId(userId);
        shoppingCart.removeItem(cartItem);
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart updateShoppingCart(String userId, Long bookId, CartItem updatedCartItem){
        ShoppingCart shoppingCart = this.findShoppingCartByUserId(userId);
        for (CartItem item : shoppingCart.getItems()) {
            if (item.getBookListingById().equals(bookId)) {
                item.setQuantity(updatedCartItem.getQuantity());
                item.setPrice(updatedCartItem.getPrice());
                break;
            }
        }
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart clearShoppingCart(String userId){
        ShoppingCart shoppingCart = this.findShoppingCartByUserId(userId);
        shoppingCart.clearItems();
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public double checkoutShoppingCart(String userId){
        ShoppingCart shoppingCart = this.findShoppingCartByUserId(userId);
        double price = shoppingCart.checkout();
        this.cartRepository.save(shoppingCart);
        return price;
    }
}
