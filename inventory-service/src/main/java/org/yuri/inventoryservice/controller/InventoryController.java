package org.yuri.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<?> getInventory() {
        final List<Inventory> inventory = this.inventoryService.getInventory();

        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }

    @GetMapping("/{upc-code}")
    public ResponseEntity<?> isInStock(@PathVariable("upc-code") final String upcCode) {
        final Boolean inStock = this.inventoryService.isInInventory(upcCode);

        return ResponseEntity.status(HttpStatus.OK).body(inStock);
    }
}
