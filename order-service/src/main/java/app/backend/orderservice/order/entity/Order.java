package app.backend.orderservice.order.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tbl_order")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private Long itemId;

	@Column(unique = true, nullable = false)
	private String orderNumber;

	@Column(nullable = false)
	private int stock;

	@Column(nullable = false)
	private Long totalPrice;

	@Column(nullable = false)
	private LocalDateTime orderDateTime;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status = OrderStatus.CREATED;

	public static Order create(
		Long memberId,
		Long itemId,
		String orderNumber,
		int stock,
		Long totalPrice,
		LocalDateTime orderDateTime
	) {

		return Order.builder()
			.memberId(memberId)
			.itemId(itemId)
			.orderNumber(orderNumber)
			.stock(stock)
			.totalPrice(totalPrice)
			.orderDateTime(orderDateTime)
			.build();
	}

	public boolean isConfirmed(){
		return OrderStatus.CONFIRMED.equals(status);
	}

	public void confirm() {
		this.status = OrderStatus.CONFIRMED;
	}

	public void pending() {
		this.status = OrderStatus.PENDING;
	}

	public void reject() {
		this.status = OrderStatus.REJECTED;
	}

	public void cancel() {
		this.status = OrderStatus.CANCELLED;
	}

}
