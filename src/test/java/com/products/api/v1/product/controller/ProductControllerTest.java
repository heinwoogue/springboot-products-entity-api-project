package com.products.api.v1.product.controller;

import com.products.api.v1.product.entity.Product;
import com.products.api.v1.product.repository.ProductRepository;
import com.products.api.v1.product.service.ProductService;
import com.products.common.exception.NotFoundException;
import com.products.common.oauth.Oauth2TestConfiguration;
import com.products.common.oauth.ResourceServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.products.api.v1.product.entity.ProductType.appliance;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @Test
    public void update_notFound() throws Exception {
        //Given
        long idToUpdate = 1L;
        String name = "Name";
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        //When
        when(productService.update(eq(idToUpdate), any(Product.class)))
                .thenThrow(new NotFoundException("Product not found."));
        ResultActions resultActions = mockMvc.perform(
                put("/api/v1/products/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":  \"" + name + "\"}")
        );

        //Then
        verify(productService, times(1))
                .update(eq(idToUpdate), productArgumentCaptor.capture());

        Product product = productArgumentCaptor.getValue();
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(name);

        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found."));
    }

    @Test
    @WithMockUser
    public void update() throws Exception {
        //Given
        long idToUpdate = 1L;
        String name = "Name";
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        Product product = new Product(
                "Updated Name",
                "Updated Description",
                appliance,
                10,
                1.2
        );
        product.setId(idToUpdate);

        //When
        when(productService.update(eq(idToUpdate), any(Product.class)))
                .thenReturn(product);
        ResultActions resultActions = mockMvc.perform(
                put("/api/v1/products/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":  \"" + name + "\"}")
        );

        //Then
        verify(productService, times(1))
                .update(eq(idToUpdate), productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct).isNotNull();
        assertThat(capturedProduct.getName()).isEqualTo(name);

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idToUpdate))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.type").value(appliance.name()))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.price").value(1.2));
    }
}
