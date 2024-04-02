package com.renyu.orderservice.dto;

import com.renyu.orderservice.model.OrderItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {

    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    public OrderItem toOrderItem() {
        return OrderItem.builder()
                .skuCode(this.skuCode)
                .price(this.price)
                .quantity(this.quantity)
                .build();

    }

}
