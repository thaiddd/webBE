package com.store.backend.Config.payment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentConfig {

    public static final String IPDEFAULT = "127.0.0.1";
    public static final String VERSIONVNPAY = "2.1.0";
    public static final String COMMAND = "pay"; // pay
    public static final String TMNCODE = "N3EA7SE7";
    public static final String CURRCODE = "VND";
    public static final String ORRDERTYPE = "";
    public static final String LOCALEDEFAULT = "vn";
    public static final String RETURNURL = "http://localhost:4200/order-confirmed";// trang tra ve ket qua
    public static final String CHECKSUM = "PYXXSBLWIHULWRXUNEPKUNKDSCTIATQN";
    public static final String VNPAYURL = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
//    public static final String RETURN

}
