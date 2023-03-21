package ca.carleton.AmazinBookStore.Listing;

import ca.carleton.AmazinBookStore.Bookstore.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long>{
}
