package ecommerce.inventoryservice.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long itemID;
    private String itemName;
    private String description;
    private Double price;
    private String imageUrl;
    private int unitsInStock;
    private List<String> categories;
}
