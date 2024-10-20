package com.store.backend.Repository;

import com.store.backend.Entity.Cart;
import com.store.backend.Entity.CartIndex;
import com.store.backend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartIndexRepository extends JpaRepository<CartIndex,Long>, CrudRepository<CartIndex,Long> {
    CartIndex findByProductAndCart(Product product, Cart cart);

    void deleteById(Long id);

    Optional<CartIndex> findById(Long id);
    List<CartIndex> findByCart(Cart cart);
}
