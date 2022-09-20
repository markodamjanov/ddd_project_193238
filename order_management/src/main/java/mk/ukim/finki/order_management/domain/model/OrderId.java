package mk.ukim.finki.order_management.domain.model;

import mk.ukim.finki.shared_kernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class OrderId extends DomainObjectId {
    protected OrderId() {
        super(OrderId.randomId(OrderId.class).getId());
    }

    protected OrderId(@NonNull String uuid) {
        super(uuid);
    }

    public static OrderId of(String uuid) {
        OrderId orderId = new OrderId(uuid);
        return orderId;
    }
}
