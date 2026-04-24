package com.stackburguer.api.service;

import com.stackburguer.api.DTO.order.OrderRequestDTO;
import com.stackburguer.api.DTO.order.OrderResponseDTO;
import com.stackburguer.api.models.User;
import com.stackburguer.api.models.order.Order;
import com.stackburguer.api.models.order.ProductItem;
import com.stackburguer.api.models.order.UserSummary;
import com.stackburguer.api.repositories.jpa.ProductRepository;
import com.stackburguer.api.repositories.mongo.OrderRepository;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;  //Interface que conversa com o Mongo

    @Autowired
    private ProductRepository productRepository;  //Interface que conversa com o Postgres

    @Autowired
    private PaymentService paymentService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    public OrderResponseDTO createOrder(OrderRequestDTO dto, User user) throws StripeException {
        List<ProductItem> items = dto.products().stream().map(itemRequest -> {
            var product = productRepository.findById(itemRequest.id())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemRequest.id()));

            return new ProductItem(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategoryId(),
                    "http://localhost:8080/product-file/" + product.getPath(),
                    itemRequest.quantity()
            );
        }).toList();

        UserSummary userSummary = new UserSummary(user.getId().toString(), user.getName());

        Order order = new Order();
        order.setUser(userSummary);
        order.setProducts(items);
        order.setStatus("Pedido realizado");

        Order savedOrder = orderRepository.save(order);

        OrderResponseDTO initialResponse = mapToResponseDTO(order, null);
        String url = paymentService.createCheckoutSession(initialResponse);

        return mapToResponseDTO(savedOrder, url);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> mapToResponseDTO(order, null))
                .toList();
    }

    private OrderResponseDTO mapToResponseDTO(Order order, String paymentUrl) {
        return new OrderResponseDTO(
                order.getId(),
                order.getUser(),
                order.getProducts(),
                order.getStatus(),
                order.getCreatedAt(),
                paymentUrl
        );
    }

    public OrderResponseDTO updateStatus(String id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        order.setStatus(status);
        return mapToResponseDTO(orderRepository.save(order), null);
    }


    public void processStripeWebhook(String payload, String sigHeader) {
        try {
            // 1. Usamos o Jackson (que o Spring já tem) para ler o texto bruto
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);

            // 2. Pegamos o tipo do evento do JSON
            String eventType = root.path("type").asText();
            System.out.println("DEBUG: Evento recebido via Jackson: " + eventType);

            if ("checkout.session.completed".equals(eventType)) {
                // 3. Navegamos no JSON: data -> object -> client_reference_id
                JsonNode sessionNode = root.path("data").path("object");
                String orderId = sessionNode.path("client_reference_id").asText();

                System.out.println("DEBUG: ID extraído manualmente: " + orderId);

                // Verificamos se o ID não é nulo ou a string "null" (que o Jackson às vezes retorna)
                if (orderId != null && !orderId.isEmpty() && !orderId.equals("null")) {
                    this.updateStatus(orderId, "Pago");
                    System.out.println("SUCESSO: Pedido " + orderId + " atualizado para Pago.");
                } else {
                    System.err.println("ALERTA: O client_reference_id não foi encontrado no JSON!");
                }
            } else {
                System.out.println("Evento " + eventType + " ignorado.");
            }

        } catch (Exception e) {
            System.err.println("ERRO ao processar JSON manualmente: " + e.getMessage());
            throw new RuntimeException("Falha no processamento do Webhook", e);
        }
    }
}
