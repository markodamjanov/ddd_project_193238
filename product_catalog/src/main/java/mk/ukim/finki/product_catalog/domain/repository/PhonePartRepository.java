package mk.ukim.finki.product_catalog.domain.repository;

import mk.ukim.finki.product_catalog.domain.models.PhonePart;
import mk.ukim.finki.product_catalog.domain.models.PhonePartId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhonePartRepository extends JpaRepository<PhonePart, PhonePartId> {
}
