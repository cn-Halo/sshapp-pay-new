package org.ssh.boot.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.ssh.boot.api.aggregate.Order;
import org.ssh.boot.api.dto.OrderCreateDTO;
import org.ssh.boot.api.event.DomainEventPublisher;
import org.ssh.boot.api.event.OrderDomainEvent;
import org.ssh.boot.api.publisher.OrderDomainEventPublisher;
import org.ssh.boot.api.event.ResultWithDomainEvents;
import org.ssh.boot.api.repository.OrderRepository;

/**
 * @author yzm
 * @date 2021/9/27 22:35
 */
public class OrderService {

    private OrderRepository orderRepository;

    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private OrderDomainEventPublisher orderDomainEventPublisher;


    public Order createOrder(OrderCreateDTO dto) {
        ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents = Order.createOrder(dto);
        Order order = orderAndEvents.result();
        orderRepository.save(order);//保存数据库
        orderDomainEventPublisher.publish(order, orderAndEvents.events());//发布领域事件
        //创建saga。。。。。
        return order;
    }

}
