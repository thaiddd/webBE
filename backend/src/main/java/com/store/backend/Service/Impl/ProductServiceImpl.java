package com.store.backend.Service.Impl;

import com.store.backend.DTO.PagingDTO;
import com.store.backend.DTO.ProductDTO;
import com.store.backend.Entity.Product;
import com.store.backend.Repository.ProductRepository;
import com.store.backend.Service.CategoryService;
import com.store.backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SimpleJdbcCall simpleJdbcCall;
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CategoryService categoryService;


    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }

    @Override
    public Product saveProduct(ProductDTO productDTO) throws IOException {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryService.searchCategoryById(productDTO.getCategoryId()));
        product.setColor(productDTO.getColor());
        product.setSex(productDTO.getSex());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setCreatedDate(LocalDate.now());
        product.setImageUrl(productDTO.getImage());
        product = productRepository.save(product);


        return productRepository.save(product);
    }

    @Override
    public boolean existProduct(Long id) {
        if (productRepository.searchProductById(id) != null) return true;
        return false;
    }

    @Override
    public boolean deleteProduct(Long id) {
        Product product = productRepository.searchProductById(id);
        productRepository.delete(product);
        return true;
    }

    @Override
    public Product searchProductById(Long id) {
        return productRepository.searchProductById(id);
    }


    @Override
    public List<Product> getByUserId(Long userId) {
        return productRepository.getProductByUserId(userId);
    }

    @Override
    public PagingDTO getPaging(PagingDTO pagingDTO) {
        if(pagingDTO.getWhere() != null){
            String where = "product_name LIKE '%"+pagingDTO.getWhere()+"%' OR "
                    + "category_name LIKE '%"+pagingDTO.getWhere()+"%' OR "
                    + "brand_name LIKE '%"+pagingDTO.getWhere()+"%'";
            pagingDTO.setWhere(where);
        }
        List<List<Map<String, Object>>> list = callStoredProcedure(pagingDTO);

        List<Map<String, Object>> listProduct = list.get(0);


        List<Map<String, Object>> count = list.get(1);

        PagingDTO result = new PagingDTO();
        result.setData(listProduct);

        result.setTotal((Long) count.get(0).get("TotalCount"));

        result.setTotalPages((int) Math.ceil((double) result.getTotal() / pagingDTO.getLimit()));
        // to do
        return result;
    }

    @Override
    public List<Product> searchProductByName(String productName) {
        return productRepository.searchProductByName(productName);
    }

    @Override
    public List<Product> FindALL() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProductNameAndCateGory(ProductDTO dto) {
        if(dto.getProductName()==null&&dto.getCategoryId()==null){
               return productRepository.findAll();
        }
        return productRepository.searchProductByNameAndCate(dto.getProductName(),dto.getCategoryId());
    }


    @Override
    public Product updateProduct(ProductDTO productDTO, Long id) throws IOException {
        Product product = productRepository.findById(id).get();
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryService.searchCategoryById(productDTO.getCategoryId()));
        product.setColor(productDTO.getColor());
        product.setSex(productDTO.getSex());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setImageUrl(productDTO.getImage());
        product.setCreatedDate(LocalDate.now());
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public List<List<Map<String, Object>>> callStoredProcedure(PagingDTO pagingDTO) {

        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("@Start", pagingDTO.getStart())
                .addValue("@Limit", pagingDTO.getLimit())
                .addValue("@Sort", pagingDTO.getSort())
                .addValue("@Where", pagingDTO.getWhere());

        List<List<Map<String, Object>>> resultSets = new ArrayList<>();
        Map<String, Object> outParams = simpleJdbcCall.withProcedureName("Proc_Product_Paging")
                .returningResultSet("result1", (rs, rowNum) -> {
                    Map<String, Object> row = new HashMap<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(columnName);
                        row.put(columnName, value);
                    }
                    return row;
                })
                .returningResultSet("result2", (rs, rowNum) -> {
                    Map<String, Object> row = new HashMap<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(columnName);
                        row.put(columnName, value);
                    }
                    return row;
                })
                .execute(inParams);
        resultSets.add((List<Map<String, Object>>) outParams.get("result1"));
        resultSets.add((List<Map<String, Object>>) outParams.get("result2"));
        return resultSets;
    }
}
