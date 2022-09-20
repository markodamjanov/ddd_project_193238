package mk.ukim.finki.order_management.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.order_management.domain.exceptions.OrderAlreadyExistsException;
import mk.ukim.finki.order_management.domain.exceptions.OrderIdNotExistException;
import mk.ukim.finki.order_management.domain.exceptions.OrderItemIdNotExistException;
import mk.ukim.finki.order_management.domain.model.*;
import mk.ukim.finki.order_management.domain.repository.OrderRepository;
import mk.ukim.finki.order_management.domain.valueobjects.PhonePart;
import mk.ukim.finki.order_management.service.OrderService;
import mk.ukim.finki.order_management.service.forms.OrderForm;
import mk.ukim.finki.order_management.service.forms.OrderItemForm;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemDeleted;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.infra.DomainEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static mk.ukim.finki.order_management.domain.model.OrderState.PROCESSED;
import static mk.ukim.finki.order_management.domain.model.OrderState.PROCESSING;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public String placeOrder() {
        Order order = this.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESSING)).findFirst().get();
        List<OrderItem> orderItemList = order.getOrderItemList().stream().toList();
        Currency currency = Currency.MKD;
        List<OrderItemForm> orderItemForms = orderItemList.stream()
                .map(x -> new OrderItemForm(new PhonePart(x.getPhonePartId(), x.getPhonePartName(), x.getPrice(), 0), x.getQuantity()))
                .collect(Collectors.toList());
        OrderForm orderForm = new OrderForm(currency, orderItemForms);
        Objects.requireNonNull(orderForm, "Order must not be null.");
        var constraintViolations = validator.validate(orderForm);
        if(constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The order form is not valid.", constraintViolations);
        }
        order.setOrderedOn(Instant.now());
        order.setOrderState(PROCESSED);
        order.setCurrency(Currency.MKD);
        order.total();
        Order newOrder = orderRepository.save(order);
        newOrder.getOrderItemList().forEach(item -> domainEventPublisher.publish(new OrderItemCreated(item.getPhonePartId().getId(), item.getQuantity())));
        return "success";
    }

    @Override
    public void createOrder() {
        if(this.findAll().stream().anyMatch(x -> x.getOrderState().equals(PROCESSING))) {
            throw new OrderAlreadyExistsException();
        }
        var newOrder = new Order();
        newOrder.setOrderState(PROCESSING);
        orderRepository.saveAndFlush(newOrder);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderItem> findAllOrderItems() {
        List<Order> order = orderRepository.findAll();

        return null;
    }

    @Override
    public void addItem(String orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.addItem(orderItemForm.getPhonePart(), orderItemForm.getQuantity());
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemCreated(orderItemForm.getPhonePart().getId().getId(), orderItemForm.getQuantity()));
    }

    @Override
    public void deleteItem(String orderId, String orderItemId) throws OrderIdNotExistException, OrderItemIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.removeItem(orderItemId);
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new OrderItemDeleted(orderItemId, 1));
    }

    private Order toDomainObject(OrderForm orderForm) {
        var order = this.findAll().stream().filter(x -> x.getOrderState().equals(PROCESSING)).findFirst().get();
        //orderForm.getItems().forEach(item -> order.addItem(item.getPhonePart(), item.getQuantity()));
        return order;
    }
}
