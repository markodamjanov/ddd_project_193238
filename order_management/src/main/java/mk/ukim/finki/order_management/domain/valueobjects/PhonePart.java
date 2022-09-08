package mk.ukim.finki.order_management.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.shared_kernel.domain.base.ValueObject;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

@Getter
public class PhonePart implements ValueObject {

    private final PhonePartId id;
    private final String name;
    private final Money price;
    private final int sales;

    private PhonePart() {
        this.id = PhonePartId.randomId(PhonePartId.class);
        this.name = "";
        this.price = Money.valueOf(Currency.MKD, 0);
        this.sales = 0;
    }

    @JsonCreator
    public PhonePart(@JsonProperty("id") PhonePartId id,
                     @JsonProperty("phonePartName") String name,
                     @JsonProperty("price") Money price,
                     @JsonProperty("sales") int sales) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sales = sales;
    }
}
