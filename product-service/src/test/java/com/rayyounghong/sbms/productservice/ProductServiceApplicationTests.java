package com.rayyounghong.sbms.productservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayyounghong.sbms.productservice.dto.ProductRequest;
import com.rayyounghong.sbms.productservice.repository.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceApplicationTests {
  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4");

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProductRepository productRepository;

  static {
    mongoDBContainer.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  public void shouldCreateProduct() throws Exception {
    ProductRequest productRequest = getProductRequest();
    String ProductRequestString = objectMapper.writeValueAsString(productRequest);
    mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
        .contentType(MediaType.APPLICATION_JSON)
        .content(ProductRequestString)
    ).andExpect(status().isCreated());
    Assertions.assertTrue(productRepository.count() > 0);
  }

  private ProductRequest getProductRequest() {
    ProductRequest productRequest = new ProductRequest();
    productRequest.setName("iPhone 13");
    productRequest.setDescription("iPhone 13");
    productRequest.setPrice(BigDecimal.valueOf(1200.99));
    return productRequest;
  }
}
