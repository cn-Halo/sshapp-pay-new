package org.ssh.boot.api.event;

import org.ssh.boot.api.aggregate.RefundInfo;

/**
 * Created on 2021/9/30.
 *
 * @author yzm
 */
public class OrderRefundedEvent implements OrderDomainEvent {

    private RefundInfo refundInfo;

    public OrderRefundedEvent(RefundInfo refundInfo) {
        this.refundInfo = refundInfo;
    }
}
