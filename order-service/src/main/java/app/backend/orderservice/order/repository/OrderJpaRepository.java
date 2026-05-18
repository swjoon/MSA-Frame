package app.backend.orderservice.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.backend.orderservice.order.entity.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
