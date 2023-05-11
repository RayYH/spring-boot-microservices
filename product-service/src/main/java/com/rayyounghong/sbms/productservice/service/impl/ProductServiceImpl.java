package com.rayyounghong.sbms.productservice.service.impl;

import com.rayyounghong.sbms.productservice.dto.ProductRequest;
import com.rayyounghong.sbms.productservice.dto.ProductResponse.ProductResponse;
import com.rayyounghong.sbms.productservice.model.Product;
import com.rayyounghong.sbms.productservice.repository.ProductRepository;
import com.rayyounghong.sbms.productservice.service.IProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
  private final ProductRepository productRepository;

  public void createProduct(ProductRequest productRequest) {
    log.info("Creating product: {}", productRequest);
    Product product = Product.builder()
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice())
        .build();
    productRepository.save(product);
    log.info("Product created: {}", product.getId());
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(this::mapToProductResponse).collect(Collectors.toList());
  }

  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();
  }
}
