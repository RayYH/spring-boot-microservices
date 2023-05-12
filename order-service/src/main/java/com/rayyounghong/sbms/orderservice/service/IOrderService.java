package com.rayyounghong.sbms.orderservice.service;

import com.rayyounghong.sbms.orderservice.dto.OrderRequest;

public interface IOrderService {
  String placeOrder(OrderRequest orderRequest);

  Integer getInventoryQuantity(String skuCode);
}
