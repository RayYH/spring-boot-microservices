package com.rayyounghong.sbms.orderservice.service;

import com.rayyounghong.sbms.orderservice.dto.InventoryResponse;
import java.util.List;

public interface IInventoryService {
  List<InventoryResponse> isInStock(List<String> skuCodes);

  Integer getInventoryQuantity(String skuCode);
}
