package mk.ukim.finki.order_management.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.order_management.domain.exceptions.OrderIdNotExistException;
import mk.ukim.finki.order_management.domain.exceptions.OrderItemIdNotExistException;
import mk.ukim.finki.order_management.domain.model.Order;
import mk.ukim.finki.order_management.domain.model.OrderId;
import mk.ukim.finki.order_management.domain.model.OrderItemId;
import mk.ukim.finki.order_management.domain.repository.OrderRepository;
import mk.ukim.finki.order_management.service.OrderService;
import mk.ukim.finki.order_management.service.forms.OrderForm;
import mk.ukim.finki.order_management.service.forms.OrderItemForm;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemDeleted;
import mk.ukim.finki.shared_kernel.infra.DomainEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrderId placeOrder(OrderForm orderForm) {
        Objects.requireNonNull(orderForm, "Order must not be null.");
        var constraintViolations = validator.validate(orderForm);
        if(constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The order form is not valid.", constraintViolations);
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        newOrder.getOrderItemList().forEach(item -> domainEventPublisher.publish(new OrderItemCreated(item.getPhonePartId().getId(), item.getQuantity())));
        return newOrder.getId();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return orderRepository.findById(id);
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.addItem(orderItemForm.getPhonePart(), orderItemForm.getQuantity());
        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getPhonePart().getId().getId(), orderItemForm.getQuantity()));
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.removeItem(orderItemId);
        domainEventPublisher.publish(new OrderItemDeleted(orderItemId.getId(), 1));
        orderRepository.saveAndFlush(order);
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(Instant.now(), orderForm.getCurrency());
        orderForm.getItems().forEach(item -> order.addItem(item.getPhonePart(), item.getQuantity()));
        return order;
    }
}
