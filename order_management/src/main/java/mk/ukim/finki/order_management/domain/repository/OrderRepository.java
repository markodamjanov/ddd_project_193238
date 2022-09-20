package mk.ukim.finki.order_management.domain.repository;

import mk.ukim.finki.order_management.domain.model.Order;
import mk.ukim.finki.order_management.domain.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
