package com.rayyounghong.sbms.productservice.service;

import com.rayyounghong.sbms.productservice.dto.ProductRequest;
import com.rayyounghong.sbms.productservice.dto.ProductResponse.ProductResponse;
import java.util.List;

public interface IProductService {

  void createProduct(ProductRequest productRequest);

  List<ProductResponse> getAllProducts();
}
