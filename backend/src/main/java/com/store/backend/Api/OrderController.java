package com.store.backend.Api;

import com.store.backend.Entity.*;
import com.store.backend.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartIndexService cartIndexService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
//        Order orders = new Order();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userService.getUserByUserName(userDetails.getUsername()).get();
        Cart cartUser = cartService.findByUser(currentUser);
        List<OrderDetail> listOrderDetail = new ArrayList<>();

        orderService.addOrder(order);


        List<CartIndex> cartIndexList = cartUser.getCartIndexs();

        for (int i = 0; i < cartIndexList.size(); i++) {
            if (cartIndexList.get(i).getState() == true) {
                Long id = cartIndexList.get(i).getId();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(cartIndexList.get(i).getQuantity());
                orderDetail.setOrders(order);
                orderDetail.setTotalPrice(cartIndexList.get(i).getTotalPrice());
                orderDetail.setProduct(cartIndexList.get(i).getProduct());
                orderDetailService.saveOrderDetail(orderDetail);
                cartIndexService.deleteIndexCartById(id);
                listOrderDetail.add(orderDetail);
            }
        }

        order.setDateOfReceipt(Date.valueOf(LocalDate.now()));
        order.setDateOfOrder(Date.valueOf(LocalDate.now()));
        order.setUser(currentUser);
        order.setState("Shipping");
        order.setUser(currentUser);
        return ResponseEntity.ok(orderService.addOrder(order));

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getOrderByUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.getByUserId(id));
    }


    @GetMapping("/finish/{id}")
    public ResponseEntity<?> finish(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(orderService.finish(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);

        }
    }

    @GetMapping("/revoke/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        return ResponseEntity.ok().body(orderService.deleteById(id));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(orderService.getByOrderById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);

        }
    }

}
