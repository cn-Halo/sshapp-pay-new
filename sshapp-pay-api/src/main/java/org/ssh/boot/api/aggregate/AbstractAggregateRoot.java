package org.ssh.boot.api.aggregate;

import org.ssh.boot.api.event.DomainEvent;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yzm
 * @date 2021/9/27 22:47
 */
public abstract class AbstractAggregateRoot implements AggregateRoot {

    Queue queue = new LinkedBlockingQueue();

    /**
     * 发布事件 两种方式
     * 第二种方式
     *
     * @param domainEvent
     */
    public void registerDomainEvent(DomainEvent domainEvent) {
        queue.add(domainEvent);
    }


}
