package org.orderservice.dto;

import lombok.Data;
import org.orderservice.model.Order;
import org.orderservice.model.OrderItem;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class RequestOrderDTO {

    @NotNull
    private List<OrderItem> items;

    public Order toOrder() {
        final Order order = new Order();

        final String orderNumber = UUID.randomUUID().toString();

        final List<OrderItem> orderItems = this.getItems()
                .stream()
                .peek(item -> item.setOrder(order)).collect(Collectors.toList());

        order.setOrderNumber(orderNumber);
        order.setItems(orderItems);

        return order;
    }

}
