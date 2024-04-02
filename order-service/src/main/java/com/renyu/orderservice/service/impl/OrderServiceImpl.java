package com.renyu.orderservice.service.impl;

import com.renyu.orderservice.dto.OrderRequestDTO;
import com.renyu.orderservice.dto.OrderResponseDTO;
import com.renyu.orderservice.model.Order;
import com.renyu.orderservice.repository.OrderRepository;
import com.renyu.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDTO placeOrder(final OrderRequestDTO orderRequestDTO) {

        final Order order = orderRequestDTO.toOrder();

        order.setOrderNumber(UUID.randomUUID().toString());

        final Order orderCreated = this.orderRepository.save(order);

        return OrderResponseDTO.fromOrder(orderCreated);
    }

}
