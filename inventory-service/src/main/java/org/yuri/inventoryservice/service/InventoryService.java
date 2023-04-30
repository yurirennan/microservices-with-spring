package org.yuri.inventoryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yuri.inventoryservice.dto.InventoryResponseDTO;
import org.yuri.inventoryservice.model.Inventory;
import org.yuri.inventoryservice.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(final InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getInventory() {
        final List<Inventory> inventoryList = this.inventoryRepository.findAll();

        final List<InventoryResponseDTO> inventoryResponseDTOList = inventoryList
                .stream()
                .map(InventoryResponseDTO::fromInventory)
                .collect(Collectors.toList());

        return inventoryResponseDTOList;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> isInInventory(final List<String> upcCode) {
        final List<Inventory> allInventory = this.inventoryRepository.findByUpcCodeIn(upcCode);

        final List<InventoryResponseDTO> allInventoryResponseDTO = allInventory
                .stream()
                .map(InventoryResponseDTO::fromInventory)
                .collect(Collectors.toList());

        return allInventoryResponseDTO;
    }
}
