package org.yuri.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yuri.inventoryservice.dto.InventoryResponseDTO;
import org.yuri.inventoryservice.model.Inventory;
import org.yuri.inventoryservice.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getInventory() {
        final List<InventoryResponseDTO> inventory = this.inventoryService.getInventory();

        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }

    @GetMapping("/products")
    public ResponseEntity<List<InventoryResponseDTO>> isInStock(@RequestParam(value = "upc-codes") final List<String> upcCodes) {
        final List<InventoryResponseDTO> allInventoryResponseDTO = this.inventoryService.isInInventory(upcCodes);

        return ResponseEntity.status(HttpStatus.OK).body(allInventoryResponseDTO);
    }
}
