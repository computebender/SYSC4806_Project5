package ca.carleton.AmazinBookStore.ShoppingCart;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<CartItem> items;

    public ShoppingCart() {
    }

    public ShoppingCart(List<CartItem> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem cartItem ) {
        this.items.add(cartItem);
    }

    public void removeItem(CartItem cartItem ) {
        this.items.remove(cartItem);
    }
    public void removeItemById(Long id ) {
        this.items.removeIf(item -> item.getId().equals(id));
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void clearItems() {
        this.items.clear();
    }

    public double checkout() {
        double total_price = 0;
        for(CartItem item : items){
            total_price += item.getPrice();
        }
        this.items.clear();
        return total_price;
    }
}
