package com.renyu.inventoryservice.service.impl;

import com.renyu.inventoryservice.dto.InventoryItemRequestDTO;
import com.renyu.inventoryservice.dto.InventoryItemResponseDTO;
import com.renyu.inventoryservice.model.InventoryItem;
import com.renyu.inventoryservice.repository.InventoryItemRepository;
import com.renyu.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(final InventoryItemRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryItemResponseDTO createItemInInventory(final InventoryItemRequestDTO inventoryItem) {
        final InventoryItem item = inventoryItem.toInventoryItem();

        /**
         * Se comunicar com o service de produto para verificar se o produto com o sku informado existe
         */

        final InventoryItem itemCreated = this.inventoryRepository.save(item);

        return InventoryItemResponseDTO.fromInventoryItem(itemCreated);
    }

}
