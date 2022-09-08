package mk.ukim.finki.order_management.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.order_management.domain.valueobjects.PhonePartId;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem extends AbstractEntity<OrderItemId> {

    @Embedded
    private Money price;

    private int quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "phone_part_id", nullable = false))
    private PhonePartId phonePartId;

    public OrderItem() {

    }

    public PhonePartId getPhonePartId() {
        return phonePartId;
    }

    public Money getPrice() {
        return price;
    }

    public OrderItem(@NonNull PhonePartId phonePartId, @NonNull Money price, int qty) {
        super(DomainObjectId.randomId(OrderItemId.class));
        this.phonePartId = phonePartId;
        this.price = price;
        this.quantity = qty;
    }

    public Money subTotal() {
        return price.multiply(quantity);
    }
}
