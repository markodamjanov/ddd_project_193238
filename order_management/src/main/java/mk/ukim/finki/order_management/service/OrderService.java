package mk.ukim.finki.order_management.service;

import mk.ukim.finki.order_management.domain.exceptions.OrderIdNotExistException;
import mk.ukim.finki.order_management.domain.exceptions.OrderItemIdNotExistException;
import mk.ukim.finki.order_management.domain.model.Order;
import mk.ukim.finki.order_management.domain.model.OrderId;
import mk.ukim.finki.order_management.domain.model.OrderItem;
import mk.ukim.finki.order_management.domain.model.OrderItemId;
import mk.ukim.finki.order_management.service.forms.OrderForm;
import mk.ukim.finki.order_management.service.forms.OrderItemForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    String placeOrder();
    void createOrder();
    List<Order> findAll();
    Optional<Order> findById(String id);
    List<OrderItem> findAllOrderItems();
    void addItem(String orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException;
    void deleteItem(String orderId, String orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException;
}
