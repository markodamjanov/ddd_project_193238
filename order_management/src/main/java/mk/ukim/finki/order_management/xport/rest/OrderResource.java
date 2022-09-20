package mk.ukim.finki.order_management.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.order_management.domain.exceptions.OrderAlreadyExistsException;
import mk.ukim.finki.order_management.domain.exceptions.OrderIdNotExistException;
import mk.ukim.finki.order_management.domain.model.Order;
import mk.ukim.finki.order_management.domain.model.OrderItem;
import mk.ukim.finki.order_management.domain.model.OrderState;
import mk.ukim.finki.order_management.domain.valueobjects.PhonePart;
import mk.ukim.finki.order_management.domain.valueobjects.PhonePartId;
import mk.ukim.finki.order_management.service.OrderService;
import mk.ukim.finki.order_management.service.forms.OrderForm;
import mk.ukim.finki.order_management.service.forms.OrderItemForm;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@AllArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/cart")
    public List<OrderItem> getCartItems() {
        return orderService.findAllOrderItems();
    }

    @PostMapping("/create")
    public String createOrder() {
        try {
            orderService.createOrder();
        } catch (OrderAlreadyExistsException e) {
            return "Order already created.";
        }
        return "success";
    }

    @PostMapping("/addOrderItem")
    public String addOrderItem(@RequestParam String id, @RequestParam String name, @RequestParam Double price) {
        OrderItemForm form = new OrderItemForm(new PhonePart(new PhonePartId(id), name, new Money(Currency.MKD, price), 1), 1);
        try {
            var order = orderService.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESSING)).findFirst().orElseThrow(OrderIdNotExistException::new);
            orderService.addItem(order.getId(), form);
            return "success";
        } catch (OrderIdNotExistException e) {
            return "order not created";
        }
    }

    @PostMapping("/deleteOrderItem")
    public String deleteOrderItem(@RequestParam String id) {
        var order = orderService.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESSING)).findFirst().get();
        var orderItem = order.getOrderItemList().stream().filter(x -> x.getId().equals(id)).findFirst().get();
        orderService.deleteItem(order.getId(), orderItem.getId());
        return "success";
    }

    @GetMapping("/getOrderItems")
    public Set<OrderItem> getOrderItems() {
        Order order = orderService.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESSING)).findFirst().get();
        return order.getOrderItemList();
    }

    @GetMapping("/seeOrder")
    public Order getOrder(@RequestParam String id) {
        return orderService.findById(id).orElseThrow(OrderIdNotExistException::new);
    }

    @PostMapping("/placeOrder")
    public void placeOrder() {
        orderService.placeOrder();
    }

    @GetMapping("/checkIfOrderIsCreated")
    public boolean checkIfProcessingOrderExist() {
        try {
            var order = orderService.findAll().stream().filter(x -> x.getOrderState().equals(OrderState.PROCESSING)).findFirst().orElseThrow(OrderIdNotExistException::new);
            if(order != null) {
                return true;
            }
        } catch (OrderIdNotExistException e) {
            return false;
        }
        return false;
    }
}
