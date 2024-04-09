package com.renyu.orderservice.service.impl;

import com.renyu.orderservice.dto.OrderItemDTO;
import com.renyu.orderservice.dto.OrderRequestDTO;
import com.renyu.orderservice.dto.OrderResponseDTO;
import com.renyu.orderservice.exceptions.ProductsNotFoundException;
import com.renyu.orderservice.grpc.inventoryservice.GRPCInventoryService;
import com.renyu.orderservice.grpc.productservice.ProductDTO;
import com.renyu.orderservice.model.Order;
import com.renyu.orderservice.repository.OrderRepository;
import com.renyu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GRPCInventoryService grpcInventoryService;

    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository, final GRPCInventoryService grpcInventoryService) {
        this.orderRepository = orderRepository;
        this.grpcInventoryService = grpcInventoryService;
    }

    @Override
    public OrderResponseDTO placeOrder(final OrderRequestDTO orderRequestDTO) {

        final List<String> skuCodes = orderRequestDTO
                .getOrderItems()
                .stream()
                .map(OrderItemDTO::getSkuCode)
                .toList();

        final List<ProductDTO> productDTOS = this.grpcInventoryService.checkIfProductInInventory(skuCodes);

        final List<String> skuCodesFromProductsOutOfStock = productDTOS
                .stream()
                .map(ProductDTO::getSkuCode)
                .toList();

        if (!productDTOS.isEmpty()) {
            throw new ProductsNotFoundException(skuCodesFromProductsOutOfStock);
        }

        final Order order = orderRequestDTO.toOrder();

        order.setOrderNumber(UUID.randomUUID().toString());

        final Order orderCreated = this.orderRepository.save(order);

        return OrderResponseDTO.fromOrder(orderCreated);
    }

}
