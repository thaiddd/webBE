package com.store.backend.Api;

import com.store.backend.DTO.CartDTO;
import com.store.backend.Entity.Cart;
import com.store.backend.Entity.CartIndex;
import com.store.backend.Entity.Product;
import com.store.backend.Entity.User;
import com.store.backend.Service.CartIndexService;
import com.store.backend.Service.CartService;
import com.store.backend.Service.ProductService;
import com.store.backend.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartIndexService cartIndexService;

    @PostMapping("")
    public  ResponseEntity<?> createCart(@RequestBody CartDTO cartDTO){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser  = userService.getUserByUserName(userDetails.getUsername()).get();
        long totalPrice = 0;
        Cart cart = cartService.findByUser(currentUser);

        // chưa có giỏ hàng thì tạo giỏ hàng
        if (cart == null) {
            cart = new Cart();
            cart.setUser(currentUser);
            cartService.save(cart);
        }
        // lấy danh sách cartIndex của giỏ hàng
        List<CartIndex> cartIndexList = cart.getCartIndexs();
        // tìm sản phẩm theo id truyền về
        Product product = productService.getById(cartDTO.getProductId());
        // tìm cartIndex của sản phẩm đó theo giỏ hàng
        CartIndex cartIndex = cartIndexService.getIndexCartByProductAndCart(product, cart);
        //nếu chưa có thì tạo mới
        if (cartIndex == null){
            cartIndex = new CartIndex();
            cartIndex.setProduct(product);
            cartIndex.setQuantity(cartDTO.getQuantity());
            cartIndex.setTotalPrice(product.getPrice()*cartDTO.getQuantity());
            cartIndex.setState(false);
            cartIndex = cartIndexService.savaIndexCart(cartIndex);
            cartIndex.setCart(cart);
            cartIndexList.add(cartIndex);
        }else{
            //nếu có rồi thì cập nhật số lượng/ giá
            for (CartIndex item : cartIndexList){
                if (item.getId() == cartIndex.getId()){
                    item.setQuantity(cartDTO.getQuantity());
                    item.setTotalPrice(cartDTO.getQuantity()*product.getPrice());
                }
            }
        }
        for (CartIndex item : cartIndexList) {
            totalPrice += item.getTotalPrice();
        }
        //
//        cart.setUser(currentUser) ;
        cart.setTotalPrice(totalPrice);
        cart.setCartIndexs(cartIndexList);
        cart = cartService.save(cart);
        currentUser.setCart(cart);
        userService.save(currentUser);

        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("")
    public ResponseEntity<?> getCartIndexList() {
        // get tên đăng nhập người dùng hệ thống
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser  = userService.getUserByUserName(userDetails.getUsername()).get();
        Cart cart = cartService.findByUser(currentUser);
//        List<CartIndex> cartIndexList = cart.getCartIndexs();
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delectCartIndexList(@PathVariable Long id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser  = userService.getUserByUserName(userDetails.getUsername()).get();
        Cart cart = cartService.findByUser(currentUser);
        CartIndex cartIndex = cartIndexService.getCartIndexById(id).get();
        cart.setTotalPrice(cart.getTotalPrice()-cartIndex.getTotalPrice());
        cartService.save(cart);
        cartIndexService.deleteIndexCartById(id);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/updateCart")
    public ResponseEntity<?> updateCart(@RequestBody Cart cart){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser  = userService.getUserByUserName(userDetails.getUsername()).get();
        Cart cartUser = cartService.findByUser(currentUser);

        List<CartIndex> cartIndexList = cartUser.getCartIndexs();
        List<CartIndex> list = cart.getCartIndexs();

        for (int i = 0; i < cartIndexList.size(); i++) {
            cartIndexList.get(i).setState(list.get(i).getState());
        }
        cartUser.setCartIndexs(cartIndexList);
        cartService.save(cartUser);

        return ResponseEntity.ok(cartUser);
    }

    @GetMapping("/getCart")
    public ResponseEntity<?> getCartTrue(){
        Cart cart = new Cart();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser  = userService.getUserByUserName(userDetails.getUsername()).get();
        Cart cartUser = cartService.findByUser(currentUser);
        long totalPrice = 0;
        List<CartIndex> list = new ArrayList<>();
        List<CartIndex> cartIndexList = cartUser.getCartIndexs();
        for (int i = 0; i < cartIndexList.size(); i++) {
            if (cartIndexList.get(i).getState() == true){
                list.add(cartIndexList.get(i));
                totalPrice+=cartIndexList.get(i).getTotalPrice();
            }
        }
        cart.setUser(currentUser);
        cart.setCartIndexs(list);
        cart.setTotalPrice(totalPrice);
        return ResponseEntity.ok(cart);
    }
}
