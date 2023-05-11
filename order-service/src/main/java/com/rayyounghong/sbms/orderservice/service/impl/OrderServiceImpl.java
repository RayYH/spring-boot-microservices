package com.rayyounghong.sbms.orderservice.service.impl;

import com.rayyounghong.sbms.orderservice.dto.OrderLineItemsDto;
import com.rayyounghong.sbms.orderservice.dto.OrderRequest;
import com.rayyounghong.sbms.orderservice.model.Order;
import com.rayyounghong.sbms.orderservice.model.OrderLineItems;
import com.rayyounghong.sbms.orderservice.repository.OrderRepository;
import com.rayyounghong.sbms.orderservice.service.IOrderService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

  private final OrderRepository orderRepository;

  public void placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList()
        .stream().map(this::mapToOrderLineItems)
        .collect(Collectors.toList());

    order.setOrderLineItemsList(orderLineItemsList);
    orderRepository.save(order);
  }

  private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
    OrderLineItems orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLineItemsDto.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return orderLineItems;
  }
}
