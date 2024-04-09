package com.renyu.inventoryservice.repository;

import com.renyu.inventoryservice.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    @Query("SELECT item FROM InventoryItem item WHERE item.skuCode in (?1) and item.quantity > 0")
    List<InventoryItem> findBySkuCodeAndQuantityGreaterThanZero(List<String> skuCodes);
}
