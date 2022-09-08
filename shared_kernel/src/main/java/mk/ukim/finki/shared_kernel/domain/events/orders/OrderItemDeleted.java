package mk.ukim.finki.shared_kernel.domain.events.orders;

import lombok.Getter;
import mk.ukim.finki.shared_kernel.domain.config.TopicHolder;
import mk.ukim.finki.shared_kernel.domain.events.DomainEvent;

@Getter
public class OrderItemDeleted extends DomainEvent {

    private String phonePartId;
    private int quantity;

    public OrderItemDeleted(String topic) {
        super(TopicHolder.TOPIC_ORDER_ITEM_DELETED);
    }

    public OrderItemDeleted(String phonePartId, int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_DELETED);
        this.phonePartId = phonePartId;
        this.quantity = quantity;
    }
}
