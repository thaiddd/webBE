package com.store.backend.Repository;

import com.store.backend.Entity.Cart;
import com.store.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUser(User user);

    Cart findByUser_Id(Long userId);

    Cart getByUser_Id(Long userId);

    boolean deleteByUser(User user);
}
