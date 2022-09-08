package mk.ukim.finki.shared_kernel.domain.events.orders;

import lombok.Getter;
import mk.ukim.finki.shared_kernel.domain.config.TopicHolder;
import mk.ukim.finki.shared_kernel.domain.events.DomainEvent;

@Getter
public class OrderItemCreated extends DomainEvent {

    private String phonePartId;
    private int quantity;

    public OrderItemCreated(String topic) {
        super(TopicHolder.TOPIC_ORDER_ITEM_CREATED);
    }

    public OrderItemCreated(String phonePartId, int quantity) {
        super(TopicHolder.TOPIC_ORDER_ITEM_CREATED);
        this.phonePartId = phonePartId;
        this.quantity = quantity;
    }

}
