package com.store.backend.Repository;

import com.store.backend.DTO.PagingDTO;
import com.store.backend.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from product ",nativeQuery = true)
    public List<Product> getAllProduct();

    @Query(value = "select * from  product where Id = ?", nativeQuery = true)
    public  Product searchProductById(Long Id);

    @Query(value = "select * from  product where product_name = ? OR id_category = ?", nativeQuery = true)
    public List<Product> searchProductByNameAndCate(String productName , Long idCategory);

    @Query(value = "select * from  product where product_name = ?", nativeQuery = true)
    public List<Product> searchProductByName(String productName);

    public List<Product> getProductByUserId(Long UserId);

    @Procedure(name = "Proc_Product_Paging")
    Map<String, Object> getPaging(
            @Param("@Start") int start,
            @Param("@Limit") int limit,
            @Param("@Sort") String sort,
            @Param("@Where") String where);

    Page<Product> findAll(Pageable pageable);
}
