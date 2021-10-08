package org.ssh.boot.api.publisher;

import org.springframework.stereotype.Service;
import org.ssh.boot.api.aggregate.Order;
import org.ssh.boot.api.event.OrderDomainEvent;

/**
 * Created on 2021/9/30.
 *
 * @author yzm
 */
@Service
public class OrderDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Order, OrderDomainEvent> {

}
