package com.rayyounghong.sbms.productservice.repository;

import com.rayyounghong.sbms.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
