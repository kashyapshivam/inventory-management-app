package com.intuit.casestudy.shivam.app.inventorymanagementapp.modals;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Builder
@Table
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int quantity;
    private String category;
    private double pricePerUnit;
    private String shelfNumber;
    private String vendorLink;


}