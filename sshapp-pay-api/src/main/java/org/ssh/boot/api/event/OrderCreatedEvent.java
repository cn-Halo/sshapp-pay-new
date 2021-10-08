package org.ssh.boot.api.event;

import org.ssh.boot.api.aggregate.Order;

/**
 * @author yzm
 * @date 2021/9/27 21:57
 */
public class OrderCreatedEvent implements OrderDomainEvent {
    private Order order;

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }


}
