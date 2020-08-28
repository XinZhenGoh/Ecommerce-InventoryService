package ecommerce.inventoryservice.inventory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ecommerce.inventoryservice.inventory.model.Product;
import ecommerce.inventoryservice.inventory.producer.ProductEventProducer;
import ecommerce.inventoryservice.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    ProductEventProducer eventProducer;

    @Autowired
    ProductRepository repository;

    @PostMapping("/v1/product")
    public ResponseEntity<Product> postProduct(@RequestBody Product product){
        try {
            //invoke producer
            repository.save(product);
            eventProducer.sendProduct(product);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //Update existing product
    @PutMapping("/v1/product")
    public ResponseEntity<?> putProduct(@RequestBody Product product){
        try {
            //invoke producer
            repository.save(product);
            eventProducer.sendProduct(product);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
