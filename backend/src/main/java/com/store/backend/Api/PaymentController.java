package com.store.backend.Api;

import com.store.backend.Config.payment.PaymentConfig;
import com.store.backend.DTO.PaymentDTO;
import com.store.backend.DTO.PaymentResDTO;
import com.store.backend.DTO.TransactionStatusDTO;
import com.store.backend.Entity.Order;
import com.store.backend.Entity.User;
import com.store.backend.Service.OrderService;
import com.store.backend.Service.UserService;
import com.store.backend.Util.VnpayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@CrossOrigin("http://localhost:4200")
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("createpayment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO requestParams) throws IOException {
        // get người dùng hiện tại
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser  = userService.getUserByUserName(userDetails.getUsername()).get();
//        OrderDTO orderDTO = orderService.orderRequest(customer.getId(), requestParams.getIdServicePack());
        Random random = new Random();
        int randomNumber = random.nextInt(10000000);
        long amount = requestParams.getAmount()*100;

        Map<String, String> vnp_Params = new HashMap<>();

        vnp_Params.put("vnp_Version", PaymentConfig.VERSIONVNPAY);
        vnp_Params.put("vnp_Command",PaymentConfig.COMMAND);
        vnp_Params.put("vnp_TmnCode",PaymentConfig.TMNCODE);
        vnp_Params.put("vnp_Amount",String.valueOf(amount));
        vnp_Params.put("vnp_BankCode",requestParams.getBankCode());
        vnp_Params.put("vnp_CurrCode",PaymentConfig.CURRCODE);
        vnp_Params.put("vnp_IpAddr",PaymentConfig.IPDEFAULT);
        vnp_Params.put("vnp_Locale",PaymentConfig.LOCALEDEFAULT);
        vnp_Params.put("vnp_OrderInfo","Thanh toan don hang");
        vnp_Params.put("vnp_OrderType",PaymentConfig.ORRDERTYPE);// loại đơn hàng
        vnp_Params.put("vnp_ReturnUrl",PaymentConfig.RETURNURL);
        vnp_Params.put("vnp_TxnRef",String.valueOf(randomNumber));

        Date dt = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = format.format(dt);

        String vnp_CreateDate = dateString;
        vnp_Params.put("vnp_CreateDate",vnp_CreateDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayUtil.hmacSHA512(VnpayUtil.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayUtil.vnp_PayUrl + "?" + queryUrl;

        PaymentResDTO result = new PaymentResDTO();
        result.setStatus("00");
        result.setMessage("success");
        result.setUrl(paymentUrl);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("infopayment") // xem lịch sử giao dịch
    public ResponseEntity<?> transactionHandle(
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode
    ) {
        TransactionStatusDTO result = new TransactionStatusDTO();
        if (!responseCode.equalsIgnoreCase("D5")){
            result.setStatus("D2");
            result.setMessage("failed");
            result.setData(null);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        return null;
    }

}
