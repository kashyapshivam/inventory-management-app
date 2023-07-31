package com.intuit.casestudy.shivam.app.inventorymanagementapp.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.casestudy.shivam.app.inventorymanagementapp.controller.InventoryController;
import com.intuit.casestudy.shivam.app.inventorymanagementapp.modals.Item;
import com.intuit.casestudy.shivam.app.inventorymanagementapp.service.InventoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(MockitoJUnitRunner.class)
public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    public void testGetAllItems() throws Exception {
        Item item1 = new Item(1L, "Item 1", 10, "Category 1", 100.0, "A1", "Vendor 1");
        Item item2 = new Item(2L, "Item 2", 20, "Category 2", 200.0, "B1", "Vendor 2");
        List<Item> items = Arrays.asList(item1, item2);

        when(inventoryService.getAllItems()).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName").value("Item 2"));
    }

    @Test
    public void testAddItem() throws Exception {
        Item newItem = new Item(3L, "Item 3", 30, "Category 3", 300.0, "C1", "Vendor 3");
        String requestBody = new ObjectMapper().writeValueAsString(newItem);

        when(inventoryService.addItem(any())).thenReturn(newItem);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Item 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Category 3"));
    }

    @Test
    public void testUpdateItem() throws Exception {
        Item updatedItem = new Item(1L, "Updated Item", 20, "Category 1", 150.0, "A1", "Vendor 1");
        String requestBody = new ObjectMapper().writeValueAsString(updatedItem);

        when(inventoryService.updateItem(any())).thenReturn(updatedItem);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Updated Item"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Category 1"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        Long itemId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/inventory/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(inventoryService, times(1)).deleteItem(itemId);
    }
}
