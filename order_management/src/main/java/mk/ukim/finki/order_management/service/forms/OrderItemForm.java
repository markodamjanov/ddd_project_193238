package mk.ukim.finki.order_management.service.forms;

import lombok.Data;
import mk.ukim.finki.order_management.domain.valueobjects.PhonePart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderItemForm {

    @NotNull
    private PhonePart phonePart;

    @Min(1)
    private int quantity = 1;
}
