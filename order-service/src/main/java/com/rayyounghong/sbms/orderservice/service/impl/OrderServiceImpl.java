package com.rayyounghong.sbms.orderservice.service.impl;

import com.rayyounghong.sbms.orderservice.dto.InventoryResponse;
import com.rayyounghong.sbms.orderservice.dto.OrderLineItemsDto;
import com.rayyounghong.sbms.orderservice.dto.OrderRequest;
import com.rayyounghong.sbms.orderservice.event.OrderPlacedEvent;
import com.rayyounghong.sbms.orderservice.model.Order;
import com.rayyounghong.sbms.orderservice.model.OrderLineItems;
import com.rayyounghong.sbms.orderservice.repository.OrderRepository;
import com.rayyounghong.sbms.orderservice.service.IOrderService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import brave.Span;
import brave.Tracer;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

  private final OrderRepository orderRepository;

  private final WebClient.Builder webClientBuilder;

  private final Tracer tracer;

  private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

  public Integer getInventoryQuantity(String skuCode) {
    return webClientBuilder.build().get()
        .uri("http://inventory-service/api/inventory/quantity", uriBuilder
            -> uriBuilder.queryParam("skuCode", skuCode).build())
        .retrieve().bodyToMono(Integer.class)
        .block();
  }

  public String placeOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList()
        .stream().map(this::mapToOrderLineItems)
        .collect(Collectors.toList());

    order.setOrderLineItemsList(orderLineItemsList);

    // call Inventory service to check if the items are available (in stock)
    // if not, throw an exception
    List<String> skuCodes = orderLineItemsList.stream()
        .map(OrderLineItems::getSkuCode)
        .collect(Collectors.toList());

    Span span = tracer.nextSpan().name("inventory-service-look-up");

    try (Tracer.SpanInScope ignored = tracer.withSpanInScope(span.start())) {
      log.info("Calling inventory-service to check the availability of products with skuCodes: {}",
          skuCodes);
      InventoryResponse[] inventoryResponses =
          webClientBuilder.build().get().uri("http://inventory-service/api/inventory", uriBuilder
                  -> uriBuilder.queryParam("skuCode", skuCodes).build())
              .retrieve().bodyToMono(InventoryResponse[].class).block();

      boolean allProductsInStock = false;
      if (inventoryResponses != null) {
        allProductsInStock = Arrays.stream(inventoryResponses)
            .allMatch(InventoryResponse::isInStock);
      }

      if (allProductsInStock) {
        orderRepository.save(order);
        log.info("Order placed successfully, order number: {}", order.getOrderNumber());
        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
        return "Order placed successfully";
      } else {
        throw new RuntimeException("Product(s) not in stock, order not placed");
      }
    }
    finally {
      span.finish();
    }
  }

  private OrderLineItems mapToOrderLineItems(OrderLineItemsDto orderLineItemsDto) {
    OrderLineItems orderLineItems = new OrderLineItems();
    orderLineItems.setPrice(orderLineItemsDto.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return orderLineItems;
  }
}
