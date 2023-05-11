package com.rayyounghong.sbms.orderservice.repository;

import com.rayyounghong.sbms.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
