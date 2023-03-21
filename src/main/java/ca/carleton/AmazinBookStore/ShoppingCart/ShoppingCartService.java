package ca.carleton.AmazinBookStore.ShoppingCart;
import ca.carleton.AmazinBookStore.Listing.Listing;

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
    private final ItemRepository itemRepository;

    public ShoppingCartService(CartRepository cartRepository, ItemRepository itemRepository){
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
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

    public ShoppingCart addShoppingCartItem(Listing listing, String userId){
        ShoppingCart shoppingCart = this.findShoppingCartByUserId(userId);
        CartItem cartItem = new CartItem();
        cartItem.setBookListing(listing);
        cartItem.setQuantity(Integer.parseInt(listing.getCopies()));
        cartItem.setPrice(Double.parseDouble(listing.getPrice()));
        cartItem.setId(listing.getId());
        this.itemRepository.save(cartItem);
        shoppingCart.addItem(cartItem);
        this.cartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart removeShoppingCartItem(Long cartItemId, String userId){
        ShoppingCart shoppingCart = this.findShoppingCartByUserId(userId);
        shoppingCart.removeItemById(cartItemId);
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
