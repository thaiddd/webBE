package com.store.backend.Service;

import com.store.backend.Entity.Cart;
import com.store.backend.Entity.CartIndex;
import com.store.backend.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface CartIndexService {

    List<CartIndex> getIndexCartByCart(Cart cart);

    CartIndex getIndexCartByProductAndCart(Product product, Cart g);

    CartIndex savaIndexCart(CartIndex cartIndex);

    void deleteIndexCart(CartIndex cartIndex);

    void  deleteIndexCartById(Long id);

    Optional<CartIndex> getCartIndexById(Long id);

    void deleteAllIndexCart(List<CartIndex> cartIndices);

}
