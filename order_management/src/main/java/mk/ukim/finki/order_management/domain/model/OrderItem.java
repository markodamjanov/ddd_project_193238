package mk.ukim.finki.order_management.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.order_management.domain.valueobjects.PhonePartId;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "order_item")
@Getter
public class OrderItem {

    @Id
    private String id;

    @Embedded
    private Money price;

    private int quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "phone_part_id", nullable = false))
    private PhonePartId phonePartId;

    private String phonePartName;

    public OrderItem() {

    }

    public PhonePartId getPhonePartId() {
        return phonePartId;
    }

    public Money getPrice() {
        return price;
    }

    public OrderItem(@NonNull PhonePartId phonePartId, @NonNull Money price, int qty, String phonePartName) {
        id = genereateID();
        this.phonePartId = phonePartId;
        this.price = price;
        this.quantity = qty;
        this.phonePartName = phonePartName;
    }

    public String genereateID() {
        Random random = new Random();
        int temp = random.nextInt()*1000;
        return "anjk-jier-"+temp+"-jb7%-bjk-kb5ds-ivbbes-7j^6";
    }

    public Money subTotal() {
        return price.multiply(quantity);
    }
}
