package com.renyu.inventoryservice.service;

import com.renyu.inventoryservice.dto.InventoryItemRequestDTO;
import com.renyu.inventoryservice.dto.InventoryItemResponseDTO;

public interface InventoryService {

    InventoryItemResponseDTO createItemInInventory(final InventoryItemRequestDTO inventoryItem);
}
