package com.renyu.inventoryservice.dto;

import com.renyu.inventoryservice.model.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponseDTO {

    private Long id;
    private String skuCode;
    private Integer quantity;

    public static InventoryItemResponseDTO fromInventoryItem(final InventoryItem item) {
        return InventoryItemResponseDTO.builder()
                .id(item.getId())
                .skuCode(item.getSkuCode())
                .quantity(item.getQuantity())
                .build();
    }
}
