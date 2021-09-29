package org.ssh.boot.api.aggregate;

import org.ssh.boot.api.dto.OrderDTO;
import org.ssh.boot.api.enums.TradeStatusEnum;
import org.ssh.boot.api.event.ResultWithDomainEvents;

/**
 * @author yzm
 * @date 2021/9/27 22:41
 */
public class Order implements AggregateRoot {

    private Long id;

    private String totalAmount;

    private TradeStatusEnum tradeStatus;

    private String subject;

    /**
     * 发布领域事件的第一种方式 将事件作为聚合方法的返回值
     *
     * @return
     */
    public static ResultWithDomainEvents<Order> createOrder(OrderDTO dto) {

        return null;
    }


}
