package com.rayyounghong.sbms.productservice.util;

import com.rayyounghong.sbms.productservice.model.Product;
import com.rayyounghong.sbms.productservice.repository.ProductRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
  private final ProductRepository productRepository;

  @Override
  public void run(String... args) throws Exception {
    if (productRepository.count() < 1) {
      productRepository.save(
          Product.builder().name("iPhone 13").description("iPhone 13")
              .price(BigDecimal.valueOf(1000)).build());
      productRepository.save(
          Product.builder().name("iPhone 13 Pro").description("iPhone 13 Pro")
              .price(BigDecimal.valueOf(1200)).build());
      productRepository.save(
          Product.builder().name("iPhone 13 Pro Max").description("iPhone 13 Pro Max")
              .price(BigDecimal.valueOf(1400)).build());
    }
  }
}
