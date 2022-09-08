package mk.ukim.finki.order_management.service;

import mk.ukim.finki.order_management.domain.exceptions.OrderIdNotExistException;
import mk.ukim.finki.order_management.domain.exceptions.OrderItemIdNotExistException;
import mk.ukim.finki.order_management.domain.model.Order;
import mk.ukim.finki.order_management.domain.model.OrderId;
import mk.ukim.finki.order_management.domain.model.OrderItemId;
import mk.ukim.finki.order_management.service.forms.OrderForm;
import mk.ukim.finki.order_management.service.forms.OrderItemForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderId placeOrder(OrderForm orderForm);
    List<Order> findAll();
    Optional<Order> findById(OrderId id);
    void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException;
    void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException;
}
