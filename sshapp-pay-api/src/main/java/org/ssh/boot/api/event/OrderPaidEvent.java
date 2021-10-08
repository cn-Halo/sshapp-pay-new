package org.ssh.boot.api.event;

import lombok.Data;
import org.ssh.boot.api.aggregate.Order;

/**
 * Created on 2021/9/30.
 *
 * @author yzm
 */
@Data
public class OrderPaidEvent implements OrderDomainEvent {

    private Order order;

    public OrderPaidEvent(Order order) {
        this.order = order;
    }
}
