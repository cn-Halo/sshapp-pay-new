package org.ssh.boot.api.event;

import org.ssh.boot.api.aggregate.AggregateRoot;

import java.util.List;

/**
 * @author yzm
 * @date 2021/9/29 21:44
 */
public class ResultWithDomainEvents<A extends AggregateRoot> {


    private A aggregate;

    private List<DomainEvent> eventList;


    public void setAggregate(A aggregate) {
        this.aggregate = aggregate;
    }

    public void setEventList(List<DomainEvent> eventList) {
        this.eventList = eventList;
    }

    public A getAggregate() {
        return aggregate;
    }

    public List<DomainEvent> getEventList() {
        return eventList;
    }
}
