package com.rayyounghong.sbms.orderservice.service;

import com.rayyounghong.sbms.orderservice.dto.OrderRequest;

public interface IOrderService {
  void placeOrder(OrderRequest orderRequest);
}
