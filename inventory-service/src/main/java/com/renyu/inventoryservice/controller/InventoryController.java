package com.renyu.inventoryservice.controller;

import com.renyu.inventoryservice.dto.InventoryItemRequestDTO;
import com.renyu.inventoryservice.dto.InventoryItemResponseDTO;
import com.renyu.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryItemResponseDTO> createItem(@RequestBody final InventoryItemRequestDTO inventoryItem) {
        final InventoryItemResponseDTO itemCreated = this.inventoryService.createItemInInventory(inventoryItem);

        return ResponseEntity.status(HttpStatus.OK).body(itemCreated);
    }

}
