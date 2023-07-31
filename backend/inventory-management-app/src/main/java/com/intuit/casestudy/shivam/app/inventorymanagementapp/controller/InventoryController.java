package com.intuit.casestudy.shivam.app.inventorymanagementapp.controller;

import com.intuit.casestudy.shivam.app.inventorymanagementapp.modals.Item;
import com.intuit.casestudy.shivam.app.inventorymanagementapp.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.OPTIONS,
        RequestMethod.GET,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.POST})
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping(produces = "application/json")
    public List<Item> getAllItems() {
        return inventoryService.getAllItems();
    }

    @PostMapping(produces = "application/json")
    public Item addItem(@RequestBody Item newItem) {
        return inventoryService.addItem(newItem);
    }

    @PutMapping("/update")
    public Item updateItem(@RequestBody Item updatedItem) {
        return inventoryService.updateItem(updatedItem);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        inventoryService.deleteItem(itemId);
    }
}