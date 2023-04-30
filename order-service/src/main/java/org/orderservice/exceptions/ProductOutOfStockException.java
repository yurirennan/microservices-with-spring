package org.orderservice.exceptions;

import lombok.Data;
import org.orderservice.dto.inventory.InventoryResponseDTO;

import java.util.List;

@Data
public class ProductOutOfStockException extends RuntimeException {

    private List<InventoryResponseDTO> productsOutOfStock;

    public ProductOutOfStockException(String message) {
        super(message);
    }

    public ProductOutOfStockException(final List<InventoryResponseDTO> productsOutOfStock) {
        this.productsOutOfStock = productsOutOfStock;
    }
}
