package com.store.backend.Api;

import com.store.backend.Config.ResponseDataConfiguration;
import com.store.backend.DTO.PagingDTO;
import com.store.backend.DTO.ProductDTO;
import com.store.backend.Entity.Product;
import com.store.backend.Service.CategoryService;
import com.store.backend.Service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;



    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    @ApiOperation("Get All Products")
    public ResponseEntity<?> getAllProducts(){
        List<Product> result = productService.getAllProduct();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{Id}")
    @ApiOperation("Get Product")
    public  ResponseEntity<Product> getProductById(@PathVariable Long Id){
        Product result = productService.searchProductById(Id);
        return  ResponseDataConfiguration.success(result);
    }


    @PostMapping()
    @ApiOperation("Save Product")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO, Authentication authentication) throws IOException {
        try {
            if (authentication != null) {
                productService.saveProduct(productDTO);
                return ResponseEntity.ok().body(null);
            }
            return ResponseEntity.badRequest().body("not authenticated");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long id,Authentication authentication){
        try {
            if (authentication != null) {
                return ResponseEntity.ok().body(productService.updateProduct(productDTO, id));
            }
            return ResponseEntity.badRequest().body("not authenticated");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable Long Id, Authentication authentication){
        try {
            if (authentication != null){
                ResponseDataConfiguration.success(productService.deleteProduct(Id));
                return ResponseEntity.ok().body(true);
            }
            return ResponseEntity.badRequest().body(false);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<List<Product>> search(@RequestParam String nameProduct){
        List<Product> result = productService.searchProductByName(nameProduct);
        return ResponseDataConfiguration.success(result);
    }

    @GetMapping("/user")
    public List<Product> getByUser(@RequestParam Long userId, Authentication authentication){
        if (authentication != null) {
            return productService.getByUserId(userId);
        }
        return null;
    }


    @PostMapping("/findAll")
    public ResponseEntity<?> getPaging() {

        return ResponseEntity.ok(productService.FindALL());

    }
    @PostMapping("/searchProduct")
    public ResponseEntity<?> searchData(@RequestBody ProductDTO dto) {

        return ResponseEntity.ok(productService.searchProductNameAndCateGory(dto));

    }

    @PostMapping("/test")
    public ResponseEntity<?> getPagingg(@RequestBody PagingDTO pagingDTO) {
//        try {
//            return ResponseEntity.ok(productService.getPaging(pagingDTO));
//        }catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }

        return  ResponseEntity.ok(productService.callStoredProcedure(pagingDTO)) ;
    }
}
