package org.ssh.boot.api.event;

/**
 * @author yzm
 * @date 2021/9/27 22:02
 */
public class DomainEventEnvelope<T extends DomainEvent> {

    private String aggregateType;
    private Object aggregateId;
    private T event;
}
