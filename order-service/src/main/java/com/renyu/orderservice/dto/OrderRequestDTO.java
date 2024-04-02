package com.renyu.orderservice.dto;

import com.renyu.orderservice.model.Order;
import com.renyu.orderservice.model.OrderItem;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderRequestDTO {

    private List<OrderItemDTO> orderItems;

    public Order toOrder() {
        final List<OrderItem> orderItems = this.getOrderItems()
                .stream()
                .map(OrderItemDTO::toOrderItem)
                .toList();

        return Order.builder()
                .orderItems(orderItems)
                .build();
    }

}
