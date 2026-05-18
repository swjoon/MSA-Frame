package app.backend.orderservice.order.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import app.backend.orderservice.order.entity.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

	private final OrderJpaRepository jpaRepository;

	@Override
	public Order createOrder(final Order order) {

		return jpaRepository.save(order);
	}

	@Override
	public Optional<Order> findOrderById(final Long orderId) {

		return jpaRepository.findById(orderId);
	}
}
