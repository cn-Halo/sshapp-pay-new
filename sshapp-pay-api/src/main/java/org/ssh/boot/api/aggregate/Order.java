package org.ssh.boot.api.aggregate;

import org.ssh.boot.api.event.DomainEvent;
import org.ssh.boot.api.event.OrderDomainEvent;

import java.util.List;

/**
 * @author yzm
 * @date 2021/9/27 22:41
 */
public class Order implements AggregateRoot {


    /**
     * 发布领域事件的第一种方式 将事件作为聚合方法的返回值
     *
     * @return
     */
    public static List<OrderDomainEvent> createOrder() {
        return null;
    }

    public void updateTradeStatus(){

    }
}
