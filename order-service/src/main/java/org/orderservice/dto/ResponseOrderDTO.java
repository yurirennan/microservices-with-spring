package org.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import org.orderservice.model.Order;
import org.orderservice.model.OrderItem;

import java.util.List;

@Data
@Builder
public class ResponseOrderDTO {

    private Long id;
    private String orderNumber;
    private List<OrderItem> items;

    public static ResponseOrderDTO toResponseOrderDTO(final Order order) {
        final ResponseOrderDTO responseOrderDTO = ResponseOrderDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .items(order.getItems())
                .build();

        return responseOrderDTO;
    }

}
