package com.products;

import com.products.api.v1.product.entity.Product;
import com.products.api.v1.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static com.products.api.v1.product.entity.ProductType.food;
import static com.products.api.v1.product.entity.ProductType.sports;

@SpringBootApplication
public class ProductsApplication {

    Logger logger = LoggerFactory.getLogger(ProductsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductRepository productRepository) {
        return (args) -> {
            logger.info("---------------------");
            logger.info("Creating products...");
            // save a couple of persons
            productRepository.save(
                    new Product(
                            "Name 1",
                            "Description 1",
                            food,
                            1,
                            1.1
                    )
            );
            productRepository.save(
                    new Product(
                            "Name 2",
                            "Description 2",
                            sports,
                            2,
                            2.2
                    )
            );
            productRepository.save(
                    new Product(
                            "Name 3",
                            "Description 3",
                            sports,
                            3,
                            3.3
                    )
            );
            List<Product> products = productRepository.findAll();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                logger.info(
                        "{}. [id: {}, name: {}, description: {}, type: {}, quantity: {}, price: {}]",
                        i + 1,
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getType(),
                        product.getQuantity(),
                        product.getPrice()
                );
            }
            logger.info("finished creating products");
            logger.info("---------------------");
        };
    }
}
