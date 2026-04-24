package com.stackburguer.api.service;

import com.stackburguer.api.DTO.order.OrderResponseDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.model.checkout.Session;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    public String createCheckoutSession(OrderResponseDTO order) throws StripeException{
        Stripe.apiKey = stripeSecretKey;

        List<SessionCreateParams.LineItem> lineItems = order.products().stream()
                .map(product -> SessionCreateParams.LineItem.builder()
                        .setQuantity((long) product.quantity())
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("brl")
                                .setUnitAmount((long) (product.price() * 100))
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(product.name())
                                        .build())
                                .build()
                        )
                        .build()
                )
                .collect(Collectors.toList());

        SessionCreateParams params = SessionCreateParams.builder()

                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/success")
                .setCancelUrl("http://localhost:5173/cancel")
                .setClientReferenceId(order.id().toString())
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);
        return session.getUrl();

    }
}
