package ecommerce.inventoryservice.inventory.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.inventoryservice.inventory.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
//give access to logger instance automatically
@Slf4j
public class ProductEventProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    //convert java object to JSON
    @Autowired
    ObjectMapper objectMapper;


    public void sendProduct(Product product) throws JsonProcessingException {
        Integer key = Math.toIntExact(product.getItemID());
        String value = objectMapper.writeValueAsString(product);

        //Asynchronous call
        ListenableFuture<SendResult<Integer, String>> listenableFuture =  kafkaTemplate.sendDefault(key,value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            //invoked if message published failed
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the message with exception: {}", ex.getMessage());
            }

            //invoked if publish is success
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }



    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message sent successfully for the key : {}, value : {}, partition : {}", key, value, result.getRecordMetadata().partition());
    }
}
