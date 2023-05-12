package com.rayyounghong.sbms.orderservice.repository;

import com.rayyounghong.sbms.orderservice.model.Inventory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  List<Inventory> findBySkuCodeIn(List<String> skuCodes);

  List<Inventory> findBySkuCode(String skuCode);
}
