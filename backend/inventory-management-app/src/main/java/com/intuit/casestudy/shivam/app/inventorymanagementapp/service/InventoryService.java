package com.intuit.casestudy.shivam.app.inventorymanagementapp.service;

import com.intuit.casestudy.shivam.app.inventorymanagementapp.modals.Item;
import com.intuit.casestudy.shivam.app.inventorymanagementapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final ItemRepository itemRepository;

    @Autowired
    public InventoryService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(Item newItem) {
        return itemRepository.save(newItem);
    }

    public Item updateItem(Item updatedItem) {
        Optional<Item> existingItem = itemRepository.findById(updatedItem.getId());
        if (existingItem.isPresent()) {
            return itemRepository.save(updatedItem);
        } else {
            throw new IllegalArgumentException("Item not found with ID: " + updatedItem.getId());
        }
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}