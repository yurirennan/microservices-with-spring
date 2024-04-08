package com.renyu.inventoryservice.service.impl;

import com.renyu.inventoryservice.dto.InventoryItemRequestDTO;
import com.renyu.inventoryservice.dto.InventoryItemResponseDTO;
import com.renyu.inventoryservice.exceptions.ProductNotFoundException;
import com.renyu.inventoryservice.grpc.productservice.GRPCProductService;
import com.renyu.inventoryservice.model.InventoryItem;
import com.renyu.inventoryservice.repository.InventoryItemRepository;
import com.renyu.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemRepository inventoryRepository;
    private final GRPCProductService grpcProductService;

    @Autowired
    public InventoryServiceImpl(final InventoryItemRepository inventoryRepository,
                                final GRPCProductService grpcProductService) {
        this.inventoryRepository = inventoryRepository;
        this.grpcProductService = grpcProductService;
    }

    @Override
    public InventoryItemResponseDTO createItemInInventory(final InventoryItemRequestDTO inventoryItem) {
        final InventoryItem item = inventoryItem.toInventoryItem();

        final boolean productExists = this.grpcProductService.checkIfProductExists(inventoryItem.getSkuCode());

        if (!productExists) {
            throw new ProductNotFoundException("PRODUCT NOT FOUND!");
        }

        final InventoryItem itemCreated = this.inventoryRepository.save(item);

        return InventoryItemResponseDTO.fromInventoryItem(itemCreated);
    }

}
