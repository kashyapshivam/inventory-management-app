package com.intuit.casestudy.shivam.app.inventorymanagementapp;

import com.intuit.casestudy.shivam.app.inventorymanagementapp.modals.Item;
import com.intuit.casestudy.shivam.app.inventorymanagementapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryManagementAppApplication implements CommandLineRunner {

	@Autowired
	ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Item item = Item.builder().id(1L).vendorLink("https://www.example.com/vendor2").category("Category B").pricePerUnit(20.5).productName("Milk").quantity(20).shelfNumber("1-b").build();
		itemRepository.save(item);

		item = Item.builder().id(2L).vendorLink("https://www.example.com/vendor2").category("Category B").pricePerUnit(30.5).productName("CURD").quantity(10).shelfNumber("1-C").build();
		itemRepository.save(item);
	}
}
