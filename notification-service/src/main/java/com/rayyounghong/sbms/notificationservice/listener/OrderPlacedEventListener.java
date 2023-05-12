package com.rayyounghong.sbms.notificationservice.listener;

import com.rayyounghong.sbms.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderPlacedEventListener {

  @KafkaListener(topics = "notificationTopic", groupId = "notificationGroup")
  public void listen(OrderPlacedEvent orderPlacedEvent) {
    log.info("Notification Service - OrderPlacedEvent consumed: {}", orderPlacedEvent);
  }
}
