package com.rayyounghong.sbms.orderservice.controller;

import com.rayyounghong.sbms.orderservice.dto.OrderRequest;
import com.rayyounghong.sbms.orderservice.service.IOrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
  private final IOrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
  public String placeOrder(@RequestBody OrderRequest orderRequest) {
    orderService.placeOrder(orderRequest);
    return "Order placed successfully";
  }

  /**
   * Fallback method for placeOrder.
   *
   * @param orderRequest Order Request
   * @param e Exception
   * @return String
   * @noinspection unused
   */
  public String placeOrderFallback(OrderRequest orderRequest, Exception e) {
    log.error("Exception is: {}", e.getMessage());
    log.info("orderRequest is: {}", orderRequest);
    return "Order placement failed, please order after some time";
  }
}
