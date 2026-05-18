package app.backend.orderservice.order.service;

import app.backend.orderservice.order.dto.req.CreateOrderReqDto;
import app.backend.orderservice.order.dto.res.CreateOrderResDto;
import app.backend.orderservice.order.dto.res.GetOrderResDto;
import app.backend.orderservice.order.entity.Order;

public interface OrderService {

	CreateOrderResDto createOrder(CreateOrderReqDto createOrderReqDto);

	GetOrderResDto readOrder(Long orderId);

	Order getOrder(Long orderId);

	Order confirmOrder(Long orderId);

	Order pendingOrder(Long orderId);

	Order rejectOrder(Long orderId);

	Order cancelOrder(Long orderId);
}
