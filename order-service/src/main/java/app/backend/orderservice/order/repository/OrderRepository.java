package app.backend.orderservice.order.repository;

import java.util.Optional;

import app.backend.orderservice.order.entity.Order;

public interface OrderRepository {

	Order createOrder(Order order);

	Optional<Order> findOrderById(Long id);

}
