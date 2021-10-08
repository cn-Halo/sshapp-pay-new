package org.ssh.boot.api.event;

import org.ssh.boot.api.aggregate.Order;

/**
 * Created on 2021/9/30.
 *
 * @author yzm
 */
public class OrderClosedEvent implements OrderDomainEvent {

    private Order order;

    public OrderClosedEvent(Order order) {
        this.order = order;
    }

}
