package mk.ukim.finki.product_catalog.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.PhonePart;
import mk.ukim.finki.product_catalog.services.PhonePartService;
import mk.ukim.finki.product_catalog.services.forms.PhonePartForm;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PhonePartResource {

    private final PhonePartService phonePartService;

    @GetMapping("/parts")
    public List<PhonePart> getAll() {
        return phonePartService.getAll();
    }

    @GetMapping("/create")
    public String createProduct() {
        PhonePartForm phonePartForm = new PhonePartForm();
        phonePartForm.setPhonePartName("Speaker");
        phonePartForm.setPrice(new Money(Currency.MKD, 4000.0));
        phonePartService.createProduct(phonePartForm);
        return "success";
    }
}
