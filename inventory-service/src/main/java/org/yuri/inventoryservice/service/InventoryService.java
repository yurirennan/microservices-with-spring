package org.yuri.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yuri.inventoryservice.model.Inventory;
import org.yuri.inventoryservice.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(final InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Inventory> getInventory() {
        final List<Inventory> inventoryList = this.inventoryRepository.findAll();

        return inventoryList;
    }

    @Transactional(readOnly = true)
    public Boolean isInInventory(final String upcCode) {
        final Optional<Inventory> inventoryOptional = this.inventoryRepository.findByUpcCode(upcCode);

        return inventoryOptional.isPresent();
    }
}
