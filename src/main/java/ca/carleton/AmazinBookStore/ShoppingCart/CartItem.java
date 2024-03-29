package ca.carleton.AmazinBookStore.ShoppingCart;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Listing.Listing;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Listing bookListing;
    private int quantity;

    private double price;

    public CartItem(Listing bookListing){
        this.bookListing = bookListing;
        this.quantity = 1;
        this.price = bookListing.getPrice();
    }

    public CartItem(){
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Listing getBookListing() {
        return bookListing;
    }

    public Long getBookListingById() {
        return bookListing.getId();
    }

    public void setBookListingById(Long id) {
        bookListing.setId(id);
    }

    public void setBookListing(Listing bookListing) {
        this.bookListing = bookListing;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
