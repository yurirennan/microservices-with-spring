package org.yuri.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.yuri.inventoryservice.model.Inventory;

@Data
@Builder
public class InventoryResponseDTO {
    private String upcCode;
    @JsonProperty("in_stock")
    private boolean inStock;

    public static InventoryResponseDTO fromInventory(final Inventory inventory) {
        final InventoryResponseDTO inventoryResponseDTO = InventoryResponseDTO.builder()
                .upcCode(inventory.getUpcCode())
                .inStock(inventory.getQuantity() > 0)
                .build();

        return inventoryResponseDTO;
    }

}
