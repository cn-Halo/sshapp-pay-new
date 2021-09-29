package org.ssh.boot.api.event;

import java.util.List;
import java.util.function.Function;

/**
 * @author yzm
 * @date 2021/9/28 18:53
 */
public abstract class AbstractAggregateDomainEventPublisher<A, E extends DomainEvent> {

    private Function<A, Object> idSupplier;

    private DomainEventPublisher eventPublisher;

    private Class<A> aggregateType;

    public void publish(A aggregate, List<E> events) {
        eventPublisher.publish(aggregateType.getName(), idSupplier.apply(aggregate), (List<DomainEvent>) events);
    }

}
