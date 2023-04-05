package ca.carleton.AmazinBookStore.ShoppingCart;
import ca.carleton.AmazinBookStore.Listing.Listing;

import ca.carleton.AmazinBookStore.User.User;
import ca.carleton.AmazinBookStore.User.UserRepository;
import ca.carleton.AmazinBookStore.User.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class ShoppingCartService {

    private final CartRepository cartRepository;

    private final ItemRepository itemRepository;
    private final UserService userService;

    public ShoppingCartService(CartRepository cartRepository, ItemRepository itemRepository, UserService userService){
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
    }
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return this.cartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> findAll() {
        return this.cartRepository.findAll();
    }

    public ShoppingCart findShoppingCartById(Long id){
        Optional<ShoppingCart> shoppingCart = this.cartRepository.findById(id);
        if(shoppingCart.isEmpty()){
            throw new ResourceNotFoundException("ShoppingCart with user ID " + id + " not found.");
        }

        return shoppingCart.get();
    }

    public ShoppingCart addShoppingCartItem(Listing listing, Long id){
        ShoppingCart shoppingCart = this.findShoppingCartById(id);
        CartItem cartItem = new CartItem();
        cartItem.setBookListing(listing);
        cartItem.setQuantity(1);
        cartItem.setPrice(listing.getPrice());
        this.itemRepository.save(cartItem);
        shoppingCart.addItem(cartItem);
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart removeShoppingCartItem(Long cartItemId, Long id){
        ShoppingCart shoppingCart = this.findShoppingCartById(id);
        shoppingCart.removeItemById(cartItemId);
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart updateShoppingCart(Long id, Long bookId, CartItem updatedCartItem){
        ShoppingCart shoppingCart = this.findShoppingCartById(id);
        for (CartItem item : shoppingCart.getItems()) {
            if (item.getId().equals(bookId)) {
                item.setQuantity(updatedCartItem.getQuantity());
                item.setPrice(updatedCartItem.getPrice());
                break;
            }
        }
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public CartItem getShoppingCartItemById(Long id, Long cartId){
        ShoppingCart shoppingCart = this.findShoppingCartById(id);
        CartItem updatedCartItem = new CartItem();
        for (CartItem item : shoppingCart.getItems()) {
            if (item.getId().equals(cartId)) {
                updatedCartItem = item;
                return updatedCartItem;
            }
        }
        return updatedCartItem;
    }

    public ShoppingCart clearShoppingCart(Long id){
        ShoppingCart shoppingCart = this.findShoppingCartById(id);
        shoppingCart.clearItems();
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public double checkoutShoppingCart(Long id){
        ShoppingCart shoppingCart = this.findShoppingCartById(id);
        List<Listing> purchaseHistory = new ArrayList<>();
        for(CartItem item: shoppingCart.getItems()){
            purchaseHistory.add(item.getBookListing());
        }
        double price = shoppingCart.checkout();
        userService.updatePurchaseHistory(shoppingCart.getUser().getId(),purchaseHistory);
        this.cartRepository.save(shoppingCart);
        return price;
    }
}
