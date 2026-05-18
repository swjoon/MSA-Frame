package app.backend.orderservice.order.event;

import app.backend.orderservice.order.entity.Order;

public interface OrderEventService {

	Order rejectOrderWithItemIncreaseEvent(final Long orderId, final Long itemId, final int stock);

	Order pendingOrderWithPaymentCheckEvent(final Long orderId, final String orderNumber, final String paymentKey);

}
