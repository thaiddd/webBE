package com.store.backend.Service.Impl;

import com.store.backend.Entity.Cart;
import com.store.backend.Entity.CartIndex;
import com.store.backend.Entity.Product;
import com.store.backend.Repository.CartIndexRepository;
import com.store.backend.Service.CartIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartIndexServiceImpl implements CartIndexService {

    @Autowired
    private CartIndexRepository repo;

    @Override
    public List<CartIndex> getIndexCartByCart(Cart cart) {
        return repo.findByCart(cart);
    }

    @Override
    public CartIndex getIndexCartByProductAndCart(Product product, Cart g) {
        return repo.findByProductAndCart(product,g);
    }

    @Override
    public CartIndex savaIndexCart(CartIndex cartIndex) {
          return  repo.save(cartIndex);
    }

    @Override
    public void deleteIndexCart(CartIndex cartIndex) {
            repo.delete(cartIndex);
    }

    @Override
    public void deleteIndexCartById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<CartIndex> getCartIndexById(Long id) {
       return repo.findById(id);
    }


    @Override
    public void deleteAllIndexCart(List<CartIndex> cartIndices) {
            repo.deleteAll(cartIndices);
    }
}
