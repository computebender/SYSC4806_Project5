package ca.carleton.AmazinBookStore.User;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Listing.Listing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "`USER`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Listing.class)
    private List<Listing> purchaseHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Listing> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<Listing> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password
                + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}

