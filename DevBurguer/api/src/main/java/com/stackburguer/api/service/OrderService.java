package com.stackburguer.api.service;

import com.stackburguer.api.DTO.order.OrderRequestDTO;
import com.stackburguer.api.DTO.order.OrderResponseDTO;
import com.stackburguer.api.models.User;
import com.stackburguer.api.models.order.Order;
import com.stackburguer.api.models.order.ProductItem;
import com.stackburguer.api.models.order.UserSummary;
import com.stackburguer.api.repositories.jpa.ProductRepository;
import com.stackburguer.api.repositories.mongo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;  //Interface que conversa com o Mongo

    @Autowired
    private ProductRepository productRepository;  //Interface que conversa com o Postgres

    public OrderResponseDTO createOrder(OrderRequestDTO dto, User user){
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

        return mapToResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private OrderResponseDTO mapToResponseDTO(Order order){
        return new OrderResponseDTO(
                order.getId(),
                order.getUser(),
                order.getProducts(),
                order.getStatus(),
                order.getCreatedAt(),
                null
        );
    }

    public OrderResponseDTO updateStatus(String id, String status){
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado"));

        order.setStatus(status);
        return mapToResponseDTO(orderRepository.save(order));
    }
}
