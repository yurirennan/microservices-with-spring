package com.renyu.productservice.dto;

import com.renyu.productservice.model.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateProductDTO {

    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;
    @NotNull(message = "price is mandatory")
    private BigDecimal price;

    public Product toProduct() {

        return Product.builder()
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .build();
    }

}
