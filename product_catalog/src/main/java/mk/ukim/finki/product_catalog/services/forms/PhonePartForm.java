package mk.ukim.finki.product_catalog.services.forms;

import lombok.Data;
import mk.ukim.finki.shared_kernel.domain.financial.Money;

@Data
public class PhonePartForm {

    private String phonePartName;
    private Money price;
    private int sales;
    private String imgUrl;
    private String description;

}
