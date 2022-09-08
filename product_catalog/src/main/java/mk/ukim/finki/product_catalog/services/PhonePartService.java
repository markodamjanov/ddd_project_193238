package mk.ukim.finki.product_catalog.services;

import mk.ukim.finki.product_catalog.domain.models.PhonePart;
import mk.ukim.finki.product_catalog.domain.models.PhonePartId;
import mk.ukim.finki.product_catalog.services.forms.PhonePartForm;

import java.util.List;

public interface PhonePartService {

    PhonePart findById(PhonePartId id);
    PhonePart createProduct(PhonePartForm form);
    PhonePart orderItemCreated(PhonePartId phonePartId, int quantity);
    PhonePart orderItemRemoved(PhonePartId phonePartId, int quantity);
    List<PhonePart> getAll();

}
