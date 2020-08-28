package ecommerce.inventoryservice.inventory.domain;

import ecommerce.inventoryservice.inventory.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEvent {

    private Integer productEventId;
    private ProductEventType eventType;
    private Product product;
}
