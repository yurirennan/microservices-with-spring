package org.orderservice.dto.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDTO {

    private String upcCode;
    @JsonProperty("in_stock")
    private boolean inStock;

}
