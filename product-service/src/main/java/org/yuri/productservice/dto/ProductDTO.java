package org.yuri.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yuri.productservice.model.Product;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal price;

    public static Product toProduct(final ProductDTO productDTO) {
        final Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .build();

        return product;
    }

    public static ProductDTO mapToProductDTO(final Product product) {
        final ProductDTO productDTO = ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();

        return productDTO;
    }
}
