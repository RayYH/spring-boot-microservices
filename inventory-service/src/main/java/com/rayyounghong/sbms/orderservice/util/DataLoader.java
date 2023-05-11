package com.rayyounghong.sbms.orderservice.util;

import com.rayyounghong.sbms.orderservice.model.Inventory;
import com.rayyounghong.sbms.orderservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
  private final InventoryRepository inventoryRepository;

  @Override
  public void run(String... args) throws Exception {
    Inventory inventory = new Inventory();
    inventory.setSkuCode("iphone-13");
    inventory.setQuantity(100);

    Inventory inventory1 = new Inventory();
    inventory1.setSkuCode("iphone-13-pro");
    inventory1.setQuantity(100);

    Inventory inventory2 = new Inventory();
    inventory2.setSkuCode("iphone-13-pro-max");
    inventory2.setQuantity(100);

    inventoryRepository.save(inventory);
    inventoryRepository.save(inventory1);
    inventoryRepository.save(inventory2);
  }
}
