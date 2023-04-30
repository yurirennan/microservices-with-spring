package org.yuri.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yuri.inventoryservice.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    List<Inventory> findByUpcCodeIn(final List<String> upcCodes);

    List<Inventory> findAll();

    void saveAll(final List<Inventory> inventoryList);
}
