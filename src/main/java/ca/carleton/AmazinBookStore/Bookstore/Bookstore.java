package ca.carleton.AmazinBookStore.Bookstore;

import ca.carleton.AmazinBookStore.Listing.Listing;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Bookstore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany()
    private List<Listing> listings;

    private String bookstoreName;

    public Bookstore() {
        this.listings = new ArrayList<Listing>();
        this.id = getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getbookstoreName() {
        return bookstoreName;
    }

    public void setbookstoreName(String bookstoreName) {
        this.bookstoreName = bookstoreName;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public void addListing(Listing listing) {
        if (listing != null) {
            listings.add(listing);
        }
    }

    public void removeListing(Long id) {
        for (Listing listing : listings) {
            if (listing.getId().equals(id)) {
                listings.remove(listing);
            }
        }
    }
}
