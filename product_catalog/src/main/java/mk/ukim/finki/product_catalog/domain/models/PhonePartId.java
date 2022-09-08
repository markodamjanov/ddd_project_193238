package mk.ukim.finki.product_catalog.domain.models;

import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class PhonePartId extends DomainObjectId {
    protected PhonePartId() {
        super(PhonePartId.randomId(PhonePartId.class).getId());
    }

    public PhonePartId(@NonNull String uuid) {
        super(uuid);
    }

    public static PhonePartId of(String uuid) {
        PhonePartId partId = new PhonePartId(uuid);
        return partId;
    }
}
