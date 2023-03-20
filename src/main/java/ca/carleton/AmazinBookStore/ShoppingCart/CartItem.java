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
        this.quantity = Integer.parseInt(bookListing.getCopies());
        this.price = Double.parseDouble(bookListing.getPrice());
    }

    public CartItem() {

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
        price = quantity * price;
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
