package org.orderservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.orderservice.dto.RequestOrderDTO;
import org.orderservice.dto.ResponseOrderDTO;
import org.orderservice.dto.inventory.InventoryResponseDTO;
import org.orderservice.exceptions.ProductOutOfStockException;
import org.orderservice.model.Order;
import org.orderservice.model.OrderItem;
import org.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Autowired
    public OrderService(final OrderRepository orderRepository, final WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    @Transactional
    public void saveOrder(final RequestOrderDTO orderDTO) {
        final Order order = orderDTO.toOrder();

        final List<String> upcCodes = order.getItems()
                .stream()
                .map(OrderItem::getUpcCode)
                .collect(Collectors.toList());

        final List<InventoryResponseDTO> productsInStock = this.webClient.get()
                .uri("http://localhost:8082/api/v1/inventory/products",
                        uriBuilder -> uriBuilder.queryParam("upc-codes", upcCodes).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<InventoryResponseDTO>>() {})
                .block();

        if (productsInStock.isEmpty()) {
            throw new RuntimeException("Os produtos não estão em estoque");
        }

        final List<InventoryResponseDTO> productsOutOfStock = productsInStock
                .stream()
                .filter(productInStock -> !productInStock.isInStock())
                .collect(Collectors.toList());

        if (!productsOutOfStock.isEmpty()) {
            throw new ProductOutOfStockException(productsOutOfStock);
        }

        this.orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<ResponseOrderDTO> listAllOrders() {
        final List<Order> orders = this.orderRepository.findAll();

        final List<ResponseOrderDTO> responseOrders = orders.stream()
                .map(ResponseOrderDTO::toResponseOrderDTO)
                .collect(Collectors.toList());

        return responseOrders;
    }
}
