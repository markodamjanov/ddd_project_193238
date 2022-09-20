package mk.ukim.finki.product_catalog.services.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.exceptions.PhonePartNotFoundException;
import mk.ukim.finki.product_catalog.domain.models.PhonePart;
import mk.ukim.finki.product_catalog.domain.models.PhonePartId;
import mk.ukim.finki.product_catalog.domain.repository.PhonePartRepository;
import mk.ukim.finki.product_catalog.services.PhonePartService;
import mk.ukim.finki.product_catalog.services.forms.PhonePartForm;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PhonePartServiceImpl implements PhonePartService {

    private final PhonePartRepository phonePartRepository;

    @Override
    public PhonePart findById(PhonePartId id) {
        return phonePartRepository.findById(id).orElseThrow(PhonePartNotFoundException::new);
    }

    @Override
    public PhonePart createProduct(PhonePartForm form) {
        PhonePart part = PhonePart.build(form.getPhonePartName(), form.getPrice(), form.getSales(), form.getImgUrl(), form.getDescription());
        phonePartRepository.save(part);
        return part;
    }

    @Override
    public PhonePart orderItemCreated(PhonePartId phonePartId, int quantity) {
        PhonePart part = phonePartRepository.findById(phonePartId).orElseThrow(PhonePartNotFoundException::new);
        part.addSales(quantity);
        phonePartRepository.saveAndFlush(part);
        return part;
    }

    @Override
    public PhonePart orderItemRemoved(PhonePartId phonePartId, int quantity) {
        PhonePart part = phonePartRepository.findById(phonePartId).orElseThrow(PhonePartNotFoundException::new);
        part.removeSales(quantity);
        phonePartRepository.saveAndFlush(part);
        return part;
    }

    @Override
    public List<PhonePart> getAll() {
        List<PhonePart> parts = phonePartRepository.findAll();
        parts.sort(Comparator.comparing(PhonePart::getPhonePartName));
        return parts;
    }

    @Override
    public void deleteProduct(String id) {
        phonePartRepository.deleteById(PhonePartId.of(id));
    }
}
