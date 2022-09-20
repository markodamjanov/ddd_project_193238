package mk.ukim.finki.product_catalog.config;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.PhonePart;
import mk.ukim.finki.product_catalog.domain.repository.PhonePartRepository;
import mk.ukim.finki.shared_kernel.domain.financial.Currency;
import mk.ukim.finki.shared_kernel.domain.financial.Money;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataInitializer {

    private final PhonePartRepository phonePartRepository;

    @PostConstruct
    public void initData() {
        PhonePart p1 = PhonePart.build("Display", Money.valueOf(Currency.MKD,5000), 3, "https://www.macfixit.com.au/media/product/8a2/iphone-11-pro-max-screen-lcd-part-only-new-7a8.jpeg", "New 2K display");
        PhonePart p2 = PhonePart.build("Battery", Money.valueOf(Currency.MKD,2000), 8, "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6257/6257171_sd.jpg", "4800mah battery");
        if (phonePartRepository.findAll().isEmpty()) {
            phonePartRepository.saveAll(Arrays.asList(p1,p2));
        }
    }
}
