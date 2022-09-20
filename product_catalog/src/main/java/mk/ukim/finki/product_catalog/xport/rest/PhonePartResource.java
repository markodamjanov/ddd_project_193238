package mk.ukim.finki.product_catalog.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.PhonePart;
import mk.ukim.finki.product_catalog.domain.models.PhonePartId;
import mk.ukim.finki.product_catalog.services.PhonePartService;
import mk.ukim.finki.product_catalog.services.forms.PhonePartForm;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@AllArgsConstructor
public class PhonePartResource {

    private final PhonePartService phonePartService;

    @GetMapping("/parts")
    public List<PhonePart> getAll() {
        return phonePartService.getAll();
    }

    @PostMapping("/create")
    public String createProduct(@RequestParam String name,
                                @RequestParam Double price,
                                @RequestParam String imgUrl,
                                @RequestParam String description) {
        PhonePartForm phonePartForm = new PhonePartForm();
        phonePartForm.setPhonePartName(name);
        phonePartForm.setPrice(new Money(Currency.MKD, price));
        phonePartForm.setImgUrl(imgUrl);
        phonePartForm.setDescription(description);
        phonePartService.createProduct(phonePartForm);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        phonePartService.deleteProduct(id);
        return "success";
    }

    @PostMapping("/addSales")
    public String increaseSales(@RequestParam String id) {
        phonePartService.orderItemCreated(PhonePartId.of(id), 1);
        return "success";
    }

    @PostMapping("/removeSales")
    public String decreaseSales(@RequestParam String id) {
        phonePartService.orderItemRemoved(PhonePartId.of(id), 1);
        return "success";
    }
}
