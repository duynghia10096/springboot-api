package com.DDN.login.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DDN.login.dto.CardToken;
import com.DDN.login.dto.PaymentStatus;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/paypal")
public class PaymentController {
    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    private String mode = "sandbox";

    private APIContext apiContext;

    @PostConstruct
    public void init() {
        this.apiContext = new APIContext(clientId, clientSecret, mode);
        
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentStatus> processPayment(@RequestBody CardToken cardToken) {

        PaymentStatus paymentStatus;
        Payment payment = new Payment();
        payment.setIntent("sale");

        // Cài đặt URL redirect khi thanh toán thành công và thất bại
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:3000/cancel"); // URL khi hủy thanh toán
        redirectUrls.setReturnUrl("http://localhost:3000/success"); // URL khi thanh toán thành công
        payment.setRedirectUrls(redirectUrls);

        // Cài đặt chi tiết giao dịch
        Amount amount = new Amount();
        amount.setCurrency(cardToken.getCurrency());
        amount.setTotal(String.format("%.2f", cardToken.getAmount().doubleValue())); // Số tiền

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Shoppers Buy");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        payment.setTransactions(transactions);

        // Cài đặt phương thức thanh toán (PayPal)
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        payment.setPayer(payer);

        try {
            // Tạo yêu cầu thanh toán
            Payment createdPayment = payment.create(apiContext);

            // Lấy URL xác nhận thanh toán từ PayPal và trả về cho frontend
            String approvalLink = null;
            for (Links link : createdPayment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    approvalLink = link.getHref();
                    break;
                }
            }

            // Trả về trạng thái thanh toán thành công
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            paymentStatus = new PaymentStatus(timestamp.getTime(), false,
                    createdPayment.getId(),
                    null,
                    approvalLink); // Gửi URL xác nhận về cho client

            return ResponseEntity.ok(paymentStatus);

        } catch (PayPalRESTException e) {
            e.printStackTrace(); // Xử lý lỗi từ PayPal

            paymentStatus = new PaymentStatus();
            paymentStatus.setPayment_failed(true);
            return ResponseEntity.badRequest().body(paymentStatus);

        } catch (Exception e) {
            e.printStackTrace(); // Xử lý các lỗi khác

            paymentStatus = new PaymentStatus();
            paymentStatus.setPayment_failed(true);
            return ResponseEntity.badRequest().body(paymentStatus);
        }
    }
}
