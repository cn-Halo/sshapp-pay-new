package org.ssh.boot.api.service;

import org.ssh.boot.api.event.DomainEventPublisher;
import org.ssh.boot.api.repository.OrderRepository;

/**
 * @author yzm
 * @date 2021/9/27 22:35
 */
public class OrderService {

    private OrderRepository orderRepository;

    private DomainEventPublisher domainEventPublisher;


    public void create() {

        //
        domainEventPublisher.publish();

    }

}
