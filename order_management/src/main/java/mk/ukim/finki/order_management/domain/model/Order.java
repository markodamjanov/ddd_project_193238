package mk.ukim.finki.order_management.domain.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import mk.ukim.finki.order_management.domain.valueobjects.PhonePart;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    private Instant orderedOn;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Id
    private String id;

    @Column(name="order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItemList;

    public Order(Instant now, @NotNull Currency currency) {
        id = genereateID();
        this.orderedOn = now;
        this.currency = currency;
    }

    public Order() {
         id = genereateID();
    }

    public Money total() {
        return orderItemList.stream().map(OrderItem::subTotal).reduce(new Money(currency, 0.0), Money::add);
    }

    public OrderItem addItem(@NonNull PhonePart phonePart, int qty) {
        Objects.requireNonNull(phonePart, "Phone part must not be null");
        var item = new OrderItem(phonePart.getId(), phonePart.getPrice(), qty, phonePart.getName());
        orderItemList.add(item);
        return item;
    }

    public String genereateID() {
        Random random = new Random();
        int temp = random.nextInt()*1000;
        return "jkbs-bdkj-"+temp+"-jb7%-bjk-kb5ds-ivbbes-7j^6";
    }

    public void removeItem(@NonNull String orderItemId) {
        Objects.requireNonNull(orderItemId, "Order item must not be null");
        orderItemList.removeIf(v -> v.getId().equals(orderItemId));
    }
}
