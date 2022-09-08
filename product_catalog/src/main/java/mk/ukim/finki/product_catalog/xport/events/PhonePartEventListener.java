package mk.ukim.finki.product_catalog.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.product_catalog.domain.models.PhonePartId;
import mk.ukim.finki.product_catalog.services.PhonePartService;
import mk.ukim.finki.shared_kernel.domain.config.TopicHolder;
import mk.ukim.finki.shared_kernel.domain.events.DomainEvent;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemCreated;
import mk.ukim.finki.shared_kernel.domain.events.orders.OrderItemDeleted;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhonePartEventListener {

    private final PhonePartService phonePartService;

    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_CREATED, groupId = "productCatalog")
    public void consumeOrderItemCreatedEvent(String jsonMessage) {
        try {
            OrderItemCreated event = DomainEvent.fromJson(jsonMessage, OrderItemCreated.class);
            phonePartService.orderItemCreated(PhonePartId.of(event.getPhonePartId()), event.getQuantity());
        } catch (Exception e) {

        }
    }

    @KafkaListener(topics = TopicHolder.TOPIC_ORDER_ITEM_DELETED, groupId = "productCatalog")
    public void consumeOrderItemDeletedEvent(String jsonMessage) {
        try {
            OrderItemDeleted event = DomainEvent.fromJson(jsonMessage, OrderItemDeleted.class);
            phonePartService.orderItemRemoved(PhonePartId.of(event.getPhonePartId()), event.getQuantity());
        } catch (Exception e) {

        }
    }

}
