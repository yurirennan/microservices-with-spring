package com.renyu.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.renyu.inventoryservice.model.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemRequestDTO {

    @JsonProperty("sku_code")
    private String skuCode;
    private Integer quantity;

    public InventoryItem toInventoryItem() {
        return InventoryItem.builder()
                .skuCode(this.skuCode)
                .quantity(this.quantity)
                .build();
    }

}
