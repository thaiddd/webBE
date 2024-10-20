package com.store.backend.Api;

import com.store.backend.Service.OrderService;
import com.store.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableWebSecurity
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/count")
    public ResponseEntity<?> getCount(){
        return ResponseEntity.ok(userService.getCount());
    }

    @GetMapping("/report-order-count/{year}")
    public ResponseEntity<?> getReportOrderByMonth(@PathVariable String year){
        return ResponseEntity.ok(orderService.getReportOrderByMonth(year));
    }
}
