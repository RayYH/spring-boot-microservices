package com.rayyounghong.sbms.orderservice.service.impl;

import com.rayyounghong.sbms.orderservice.dto.InventoryResponse;
import com.rayyounghong.sbms.orderservice.repository.InventoryRepository;
import com.rayyounghong.sbms.orderservice.service.IInventoryService;
import java.util.List;
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
    return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
        .map(
            inventory -> InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .build()
        ).collect(Collectors.toList());
  }
}
