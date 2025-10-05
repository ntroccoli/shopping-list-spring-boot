package com.nelsontr.shoppinglist.config;

import com.nelsontr.shoppinglist.model.Item;
import com.nelsontr.shoppinglist.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadInitialData(ItemRepository itemRepository) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Item>> typeReference = new TypeReference<>() {};
            InputStream inputStream = getClass().getResourceAsStream("/data.json");
            try {
                List<Item> items = mapper.readValue(inputStream, typeReference);
                itemRepository.saveAll(items);
                System.out.println("Initial data loaded successfully");
            } catch (Exception e) {
                System.err.println("Error loading initial data: " + e.getMessage());
            }
        };
    }
}