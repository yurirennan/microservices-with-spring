package org.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_orders_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("upc_code")
    private String upcCode;
    private BigDecimal price;
    private Integer quantity;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
