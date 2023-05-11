package com.rayyounghong.sbms.orderservice.controller;

import com.rayyounghong.sbms.orderservice.dto.InventoryResponse;
import com.rayyounghong.sbms.orderservice.service.IInventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

  private final IInventoryService inventoryService;

  /**
   * Check if the given skus are in stock.
   *
   * @param skuCode sku code
   * @return inventory response
   * @see InventoryResponse
   * http://localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone-13-pro
   * {@code curl -X GET "http://localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone-13-pro" -H "accept: application/json"}
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
    log.info("Checking inventory for skus {}", skuCode);
    log.info("{}", inventoryService.isInStock(skuCode));
    return inventoryService.isInStock(skuCode);
  }
}
