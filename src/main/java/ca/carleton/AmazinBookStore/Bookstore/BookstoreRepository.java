package ca.carleton.AmazinBookStore.Bookstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
}
