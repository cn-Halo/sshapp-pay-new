package org.ssh.boot.api.service;

import org.ssh.boot.api.aggregate.Order;
import org.ssh.boot.api.event.AbstractAggregateDomainEventPublisher;
import org.ssh.boot.api.event.DomainEventPublisher;
import org.ssh.boot.api.event.OrderDomainEvent;
import org.ssh.boot.api.repository.OrderRepository;

import java.util.List;

/**
 * @author yzm
 * @date 2021/9/27 22:35
 */
public class OrderService extends AbstractAggregateDomainEventPublisher<Order, OrderDomainEvent> {

    private OrderRepository orderRepository;

    private DomainEventPublisher domainEventPublisher;


    public void create() {
        List<OrderDomainEvent> events = Order.createOrder();
        //如何将对象保存到数据库
        publish(null, events);


    }

}
