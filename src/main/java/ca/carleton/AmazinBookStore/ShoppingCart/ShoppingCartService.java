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
            if(shoppingCart1.getId().equals(shoppingCart.getId())){
                throw new DuplicateKeyException("ShoppingCart with user ID " + shoppingCart.getId() + " already exists.");
            }
        }

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
        double price = shoppingCart.checkout();
        this.cartRepository.save(shoppingCart);
        return price;
    }
}
