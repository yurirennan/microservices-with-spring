package com.renyu.orderservice.dto;

import com.renyu.orderservice.model.Order;
import com.renyu.orderservice.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private String orderNumber;
    private List<OrderItemResponseDTO> orderItems;

    public static OrderResponseDTO fromOrder(final Order order) {
        final List<OrderItem> orderItems = order.getOrderItems();

        final List<OrderItemResponseDTO> orderItemResponse = orderItems
                .stream()
                .map(OrderItemResponseDTO::fromOrderItem)
                .toList();

        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderItems(orderItemResponse)
                .build();
    }

}
