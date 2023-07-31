package com.intuit.casestudy.shivam.app.inventorymanagementapp.repository;

import com.intuit.casestudy.shivam.app.inventorymanagementapp.modals.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}