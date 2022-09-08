package mk.ukim.finki.order_management.domain.valueobjects;

import lombok.NonNull;
import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class PhonePartId extends DomainObjectId {

    protected PhonePartId(@NonNull String uuid) {
        super(uuid);
    }

    public PhonePartId() {
        super();
    }
}
