package com.renyu.productservice.dto;

import com.renyu.productservice.model.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDTO {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    public static ProductDTO fromProduct(final Product product) {
        return ProductDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
