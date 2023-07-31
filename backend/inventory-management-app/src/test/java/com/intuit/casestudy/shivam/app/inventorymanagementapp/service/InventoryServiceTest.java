package com.intuit.casestudy.shivam.app.inventorymanagementapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.intuit.casestudy.shivam.app.inventorymanagementapp.modals.Item;
import com.intuit.casestudy.shivam.app.inventorymanagementapp.repository.ItemRepository;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Item item1;
    private Item item2;

    @Before
    public void setup() {
        item1 = new Item(1L, "Item 1", 10, "Category 1", 100.0, "A1", "Vendor 1");
        item2 = new Item(2L, "Item 2", 20, "Category 2", 200.0, "B1", "Vendor 2");
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        when(itemRepository.findAll()).thenReturn(items);

        List<Item> result = inventoryService.getAllItems();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(item1, result.get(0));
        assertEquals(item2, result.get(1));
    }

    @Test
    public void testAddItem() {
        Item newItem = new Item(3L, "Item 3", 30, "Category 3", 300.0, "C1", "Vendor 3");

        when(itemRepository.save(any())).thenReturn(newItem);

        Item result = inventoryService.addItem(newItem);

        assertNotNull(result);
        assertEquals(newItem, result);
    }

    @Test
    public void testUpdateItem() {
        Item updatedItem = new Item(1L, "Updated Item", 20, "Category 1", 150.0, "A1", "Vendor 1");

        when(itemRepository.findById(updatedItem.getId())).thenReturn(Optional.of(item1));
        when(itemRepository.save(any())).thenReturn(updatedItem);

        Item result = inventoryService.updateItem(updatedItem);

        assertNotNull(result);
        assertEquals(updatedItem, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateItem_ItemNotFound() {
        Item updatedItem = new Item(1L, "Updated Item", 20, "Category 1", 150.0, "A1", "Vendor 1");

        when(itemRepository.findById(updatedItem.getId())).thenReturn(Optional.empty());

        inventoryService.updateItem(updatedItem);
    }

    @Test
    public void testDeleteItem() {
        Long itemId = 1L;

        doNothing().when(itemRepository).deleteById(itemId);

        inventoryService.deleteItem(itemId);

        verify(itemRepository, times(1)).deleteById(itemId);
    }
}