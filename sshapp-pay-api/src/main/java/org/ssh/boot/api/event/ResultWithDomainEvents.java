package org.ssh.boot.api.event;

import lombok.Data;
import org.ssh.boot.api.aggregate.AggregateRoot;

import java.util.List;

/**
 * @author yzm
 * @date 2021/9/29 21:44
 */
@Data
public class ResultWithDomainEvents<A extends AggregateRoot, E extends DomainEvent> {


    private A aggregate;

    private List<E> events;


    public ResultWithDomainEvents(A a, List<E> events) {
        this.aggregate = a;
        this.events = events;
    }

    public A result() {
        return aggregate;
    }

    public List<E> events() {
        return events;
    }


}
