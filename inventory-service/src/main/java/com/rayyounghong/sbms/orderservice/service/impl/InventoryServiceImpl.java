package com.rayyounghong.sbms.orderservice.service.impl;

import com.rayyounghong.sbms.orderservice.dto.InventoryResponse;
import com.rayyounghong.sbms.orderservice.model.Inventory;
import com.rayyounghong.sbms.orderservice.repository.InventoryRepository;
import com.rayyounghong.sbms.orderservice.service.IInventoryService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements IInventoryService {

  private final InventoryRepository inventoryRepository;

  @Transactional(readOnly = true)
  @SneakyThrows
  @Override
  public List<InventoryResponse> isInStock(List<String> skuCodes) {
    log.info("Checking inventory for skus {}", skuCodes);

    // use skuCode as the key, and Inventory as the value
    Map<String, Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCodes)
        .stream()
        .collect(Collectors.toMap(Inventory::getSkuCode, inventory -> inventory, (a, b) -> a));

    // determine each skuCode is in stock or not
    return skuCodes.stream().map(skuCode -> {
      InventoryResponse inventoryResponse = new InventoryResponse();
      inventoryResponse.setSkuCode(skuCode);
      if (inventories.get(skuCode) == null) {
        inventoryResponse.setInStock(false);
        return inventoryResponse;
      }
      inventoryResponse.setInStock(inventories.get(skuCode).getQuantity() > 0);
      return inventoryResponse;
    }).collect(Collectors.toList());
  }
}
