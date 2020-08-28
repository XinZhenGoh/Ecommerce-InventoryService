package ecommerce.inventoryservice.inventory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ecommerce.inventoryservice.inventory.domain.ProductEvent;
import ecommerce.inventoryservice.inventory.domain.ProductEventType;
import ecommerce.inventoryservice.inventory.producer.ProductEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    ProductEventProducer eventProducer;

    @PostMapping("/v1/productevent")
    public ResponseEntity<ProductEvent> postProductEvent(@RequestBody ProductEvent productEvent){
        try {
            //invoke producer
            productEvent.setEventType(ProductEventType.NEW);
            eventProducer.sendProductEvent(productEvent);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productEvent);
    }

    //Update existing product
    @PutMapping("/v1/productevent")
    public ResponseEntity<?> putProductEvent(@RequestBody ProductEvent productEvent){
        try {
            //invoke producer
            if(productEvent.getProductEventId() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No product ID specified");
            }
            productEvent.setEventType(ProductEventType.UPDATE);
            eventProducer.sendProductEvent(productEvent);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

        return ResponseEntity.status(HttpStatus.OK).body(productEvent);
    }
}
