package com.store.backend.Service;

import com.store.backend.DTO.PagingDTO;
import com.store.backend.DTO.ProductDTO;
import com.store.backend.Entity.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProduct();
    Product saveProduct(ProductDTO productDTO) throws IOException;
    boolean existProduct(Long id);
    boolean deleteProduct(Long id);
    Product searchProductById(Long id);
    List<Product> getByUserId(Long userId);
    PagingDTO getPaging(PagingDTO pagingDTO);

    List<Product> searchProductByName(String productName);
    List<Product>FindALL();
    List<Product>searchProductNameAndCateGory(ProductDTO dto);
    Product updateProduct(ProductDTO productDTO, Long id) throws IOException;

    Product getById(Long productId);

    List<List<Map<String, Object>>> callStoredProcedure(PagingDTO pagingDTO);
}
