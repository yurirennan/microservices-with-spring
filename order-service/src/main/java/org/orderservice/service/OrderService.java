package org.orderservice.service;

import org.orderservice.dto.RequestOrderDTO;
import org.orderservice.dto.ResponseOrderDTO;
import org.orderservice.model.Order;
import org.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void saveOrder(final RequestOrderDTO orderDTO) {
        final Order order = orderDTO.toOrder();
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
