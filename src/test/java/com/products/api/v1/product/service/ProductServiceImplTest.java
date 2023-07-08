package com.products.api.v1.product.service;

import com.products.api.v1.product.entity.Product;
import com.products.api.v1.product.repository.ProductRepository;
import com.products.common.exception.NotFoundException;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.products.api.v1.product.entity.ProductType.music;
import static com.products.api.v1.product.entity.ProductType.sports;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ProductServiceImpl.class)
public class ProductServiceImplTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Test
    public void update_nonExisting(){
        long idToUpdate = 4L;

        //Given
        assertThat(productRepository.existsById(idToUpdate)).isFalse();

        //When
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> productService.update(idToUpdate, new Product())
        );

        //Then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Product not found");
    }

    @Test
    public void update_existing(){
        long idToUpdate = 3L;

        //Given
        Product existingProduct = productRepository.findById(idToUpdate)
                .orElse(null);
        assertThat(existingProduct).isNotNull();
        assertThat(existingProduct.getId()).isEqualTo(idToUpdate);
        assertThat(existingProduct.getName()).isEqualTo("Name 3");
        assertThat(existingProduct.getDescription()).isEqualTo("Description 3");
        assertThat(existingProduct.getType()).isEqualTo(sports);
        assertThat(existingProduct.getQuantity()).isEqualTo(3L);
        assertThat(existingProduct.getPrice()).isEqualTo(3.3);

        //When
        Product updatedProduct = productService.update(
                idToUpdate,
                new Product(
                        "Updated Name 3",
                        "Updated Description 3",
                        music,
                        4L,
                        3.4
                )
        );

        //Then
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getId()).isEqualTo(idToUpdate);
        assertThat(updatedProduct.getName()).isEqualTo("Updated Name 3");
        assertThat(updatedProduct.getDescription()).isEqualTo("Updated Description 3");
        assertThat(updatedProduct.getType()).isEqualTo(music);
        assertThat(updatedProduct.getQuantity()).isEqualTo(4L);
        assertThat(updatedProduct.getPrice()).isEqualTo(3.4);

        Product newProductInDb = productRepository.findById(idToUpdate)
                .orElse(null);
        assertThat(newProductInDb).isNotNull();
        assertThat(newProductInDb.getId()).isEqualTo(idToUpdate);
        assertThat(newProductInDb.getName()).isEqualTo("Updated Name 3");
        assertThat(newProductInDb.getDescription()).isEqualTo("Updated Description 3");
        assertThat(newProductInDb.getType()).isEqualTo(music);
        assertThat(newProductInDb.getQuantity()).isEqualTo(4L);
        assertThat(newProductInDb.getPrice()).isEqualTo(3.4);
    }
}
