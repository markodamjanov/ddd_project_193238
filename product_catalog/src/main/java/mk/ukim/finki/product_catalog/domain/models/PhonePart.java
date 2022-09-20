package mk.ukim.finki.product_catalog.domain.models;

import lombok.Getter;
import mk.ukim.finki.shared_kernel.domain.base.AbstractEntity;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

import javax.persistence.*;

@Entity
@Table(name="phone_part")
@Getter
public class PhonePart extends AbstractEntity<PhonePartId> {

    private String phonePartName;

    private int numberOfSales = 0;

    private String imgUrl;

    private String description;

    @AttributeOverrides({
            @AttributeOverride(name="amount", column = @Column(name="price_amount")),
            @AttributeOverride(name="currency", column = @Column(name="price_currency"))
    })
    @Embedded
    private Money price;

    public Money getPrice() {
        return price;
    }

    public PhonePart() {
        super(PhonePartId.randomId(PhonePartId.class));
    }

    public static PhonePart build(String phonePartName, Money price, int numberOfSales, String imgUrl, String description) {
        PhonePart p = new PhonePart();
        p.price = price;
        p.phonePartName = phonePartName;
        p.numberOfSales = numberOfSales;
        p.imgUrl = imgUrl;
        p.description = description;
        return p;
    }

    public void addSales(int qty) {
        this.numberOfSales = this.numberOfSales + qty;
    }

    public void removeSales(int qty) {
        this.numberOfSales -= qty;
    }

}
