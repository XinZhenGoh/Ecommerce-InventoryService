package ecommerce.inventoryservice.inventory.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//to be present in spring application context
@Configuration
public class AutoCreateConfig {

    @Bean
    public NewTopic productEvents(){
        return TopicBuilder.name("product-events")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
