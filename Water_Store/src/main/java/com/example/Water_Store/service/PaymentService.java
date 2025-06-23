package com.example.Water_Store.service;

import com.example.Water_Store.dto.OrderResponse; // Keep if you still need this DTO for something else, but it's not used directly for the PayPal response now.
import com.example.Water_Store.dto.PaymentRequest;
import com.example.Water_Store.entity.Order;
import com.example.Water_Store.repository.OrderRepository;
import com.paypal.orders.OrderRequest; // Corrected import for OrderRequest
import com.paypal.orders.ApplicationContext; // Corrected import for ApplicationContext
import com.paypal.orders.PurchaseUnitRequest; // Corrected import for PurchaseUnitRequest
import com.paypal.orders.AmountWithBreakdown; // Corrected import for AmountWithBreakdown
import com.paypal.orders.OrdersCreateRequest; // Corrected import for OrdersCreateRequest
import com.paypal.orders.LinkDescription; // Corrected import for LinkDescription
import com.paypal.core.PayPalHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PayPalHttpClient payPalClient;
    private final OrderRepository orderRepo;

    public String createPayment(PaymentRequest request) throws IOException {
        Order order = orderRepo.findByTrackingId(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderRequest payRequest = new OrderRequest();
        payRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext context = new ApplicationContext()
                .returnUrl("http://localhost:8080/api/payment/success?trackingId=" + order.getTrackingId())
                .cancelUrl("http://localhost:8080/api/payment/cancel");

        payRequest.applicationContext(context);
        payRequest.purchaseUnits(List.of(
                new PurchaseUnitRequest()
                        .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode(request.getCurrency())
                                .value(String.format("%.2f", request.getAmount())))
                        .description("Payment for Order: " + order.getTrackingId())
        ));

        OrdersCreateRequest requestObj = new OrdersCreateRequest().requestBody(payRequest);
        // FIX: Change the type from com.example.Water_Store.dto.OrderResponse to com.paypal.orders.Order
        com.paypal.orders.Order response = payPalClient.execute(requestObj).result();

        return response.links().stream()
                .filter(link -> "approve".equalsIgnoreCase(link.rel()))
                .findFirst()
                .map(LinkDescription::href) // Use LinkDescription.href()
                .orElseThrow();
    }

    public String markOrderAsPaid(String trackingId) {
        Order order = orderRepo.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("PAID");
        orderRepo.save(order);
        return "Order marked as PAID";
    }
}