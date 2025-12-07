package dev.chilito.backend.inventory.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.chilito.backend.inventory.application.output.IProductOutPort;
import dev.chilito.backend.inventory.application.usecases.ProductService;

@Configuration
public class InventoryConfig {

    @Bean
    public ProductService createProductServiceBean(IProductOutPort productOutPort) {
        return new ProductService(productOutPort);
    }
}
