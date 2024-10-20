package com.store.backend.Service;

import com.store.backend.Entity.Cart;
import com.store.backend.Entity.User;

public interface CartService {
    Cart getByUserId(Long id);

    Cart save( Cart cart);

    Cart findByUser(User byUserId);
}
