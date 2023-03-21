package ca.carleton.AmazinBookStore.Listing;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import jakarta.persistence.*;

@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookstore_id")
    private Bookstore location;
    private Double price;
    private Integer copies;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book; //placeholder for book object


    public Listing(Bookstore location, Double price, Integer copies, Book book){
        this.location = location;
        this.price = price;
        this.copies = copies;
        this.book = book;
    }

    public Listing() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bookstore getLocation() {
        return location;
    }

    public void setLocation(Bookstore location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
