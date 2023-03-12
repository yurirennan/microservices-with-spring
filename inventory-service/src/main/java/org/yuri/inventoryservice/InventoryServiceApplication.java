package org.yuri.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.yuri.inventoryservice.model.Inventory;
import org.yuri.inventoryservice.repository.InventoryRepository;

import java.util.Arrays;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(final InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setUpcCode("123456789010");
			inventory.setQuantity(10);

			Inventory inventory2 = new Inventory();
			inventory2.setUpcCode("123456789011");
			inventory2.setQuantity(11);

			Inventory inventory3 = new Inventory();
			inventory3.setUpcCode("123456789012");
			inventory3.setQuantity(12);

			inventoryRepository.saveAll(Arrays.asList(inventory, inventory2, inventory3));
		};
	}

}
