package org.yuri.inventoryservice.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.yuri.inventoryservice.dto.InventoryResponseDTO;
import org.yuri.inventoryservice.model.Inventory;
import org.yuri.inventoryservice.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public InventoryRepositoryImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Inventory> findByUpcCodeIn(final List<String> upcCodes) {
        final String sql = "SELECT ti.upc_code, ti.quantity FROM t_inventory ti WHERE ti.upc_code IN (:upc_codes);";

        final Map<String, Object> params = new HashMap<>();
        params.put("upc_codes", upcCodes);

        final ResultSetExtractor<List<Inventory>> extractor = resultSet -> {
            final List<Inventory> inventoryList = new ArrayList<>();

            while(resultSet.next()) {
                final String upcCode = resultSet.getString("upc_code");
                final Integer quantity = resultSet.getInt("quantity");

                final Inventory inventory = Inventory.builder()
                        .upcCode(upcCode)
                        .quantity(quantity)
                        .build();

                inventoryList.add(inventory);
            }

            upcCodes.forEach(upcCode -> {
                final boolean match = inventoryList.stream().anyMatch(inventory -> inventory.getUpcCode().equals(upcCode));

                if (!match) {
                    final Inventory inventory = Inventory.builder()
                            .upcCode(upcCode)
                            .quantity(0)
                            .build();

                    inventoryList.add(inventory);
                }
            });

            return inventoryList;
        };

        final List<Inventory> inventoryList = this.namedParameterJdbcTemplate.query(sql, params, extractor);

        return inventoryList;
    }

    @Override
    public List<Inventory> findAll() {
        final String sql = "SELECT ti.upc_code, ti.quantity FROM t_inventory ti;";

        final ResultSetExtractor<List<Inventory>> extractor = resultSet -> {
            final List<Inventory> inventoryList = new ArrayList<>();

            while(resultSet.next()) {
                final String upcCode = resultSet.getString("upc_code");
                final Integer quantity = resultSet.getInt("quantity");

                final Inventory inventory = Inventory.builder()
                        .upcCode(upcCode)
                        .quantity(quantity)
                        .build();

                inventoryList.add(inventory);
            }

            return inventoryList;
        };

        final List<Inventory> inventoryList = this.namedParameterJdbcTemplate.query(sql, extractor);

        return inventoryList;
    }

    @Override
    public void saveAll(final List<Inventory> inventoryList) {
        final StringBuilder sqlTemplate = new StringBuilder("INSERT INTO t_inventory (quantity, upc_code) VALUES ");

        for (int i = 0; i < inventoryList.size(); i++) {
            final Inventory inventory = inventoryList.get(i);

            sqlTemplate.append("( " + inventory.getQuantity() + " , " + inventory.getUpcCode() + ")");

            if (i < inventoryList.size() - 1) {
                sqlTemplate.append(",");
            } else {
                sqlTemplate.append(";");
            }
        }

        final String sqlQuery = sqlTemplate.toString();

        final Map<String, Object> params = new HashMap<>();

        this.namedParameterJdbcTemplate.update(sqlQuery, params);
    }

}
