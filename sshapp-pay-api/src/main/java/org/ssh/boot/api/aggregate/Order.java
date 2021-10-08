package org.ssh.boot.api.aggregate;

import lombok.Data;
import org.ssh.boot.api.dto.OrderCreateDTO;
import org.ssh.boot.api.dto.OrderPaidDTO;
import org.ssh.boot.api.dto.OrderRefundDTO;
import org.ssh.boot.api.enums.TradeStatusEnum;
import org.ssh.boot.api.event.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author yzm
 * @date 2021/9/27 22:41
 */
@Data
public class Order implements AggregateRoot {

    private Long id;

    private TradeStatusEnum tradeStatus;

    private String totalRefundAmount;

    private PaymentInfo paymentInfo;


    /**
     * 发布领域事件的第一种方式 将事件作为聚合方法的返回值
     *
     * @return
     */
    public static ResultWithDomainEvents<Order, OrderDomainEvent> createOrder(OrderCreateDTO dto) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setSubject(dto.getSubject());
        paymentInfo.setOutTradeNo(dto.getOutTradeNo());
        paymentInfo.setPaymentAmount(dto.getPaymentAmount());
        Order order = new Order();
        order.setPaymentInfo(paymentInfo);
        order.setTradeStatus(TradeStatusEnum.WAIT_BUYER_PAY);

        List<OrderCreatedEvent> events = Collections.singletonList(new OrderCreatedEvent(order));
        return new ResultWithDomainEvents(order, events);
    }


    public ResultWithDomainEvents<Order, OrderDomainEvent> closed() {
        if(TradeStatusEnum.){

        }
        this.tradeStatus = TradeStatusEnum.TRADE_CLOSED;
        List<OrderClosedEvent> events = Collections.singletonList(new OrderClosedEvent(this));
        return new ResultWithDomainEvents(this, events);
    }

    public ResultWithDomainEvents<Order, OrderDomainEvent> paid(OrderPaidDTO dto) {

        this.paymentInfo.setGmtPayment(dto.getTradeNo());
        this.paymentInfo.setGmtPayment(dto.getGmtPayment());
        this.tradeStatus = TradeStatusEnum.TRADE_SUCCESS;

        List<OrderPaidEvent> events = Collections.singletonList(new OrderPaidEvent(this));
        return new ResultWithDomainEvents(this, events);
    }

    public ResultWithDomainEvents<Order, OrderDomainEvent> refunded(OrderRefundDTO dto) {
        refundTradeStatusCheck();
        refundAmountCheck(dto.getRefundAmount());

        this.totalRefundAmount = new BigDecimal(dto.getRefundAmount()).add(new BigDecimal(this.totalRefundAmount == null ? "0.0" : this.totalRefundAmount)).toString();
        this.tradeStatus = TradeStatusEnum.TRADE_REFUND;

        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setRefundAmount(dto.getRefundAmount());
        refundInfo.setOutRefundNo(dto.getOutRefundNo());
        refundInfo.setRefundReason(dto.getRefundReason());
        refundInfo.setGmtRefundPay(dto.getGmtRefundPay());
        refundInfo.setRefundNo(dto.getRefundNo());
        refundInfo.setOutTradeNo(dto.getOutTradeNo());
        refundInfo.setTradeStatus(this.tradeStatus);

        List<OrderRefundedEvent> events = Collections.singletonList(new OrderRefundedEvent(refundInfo));
        return new ResultWithDomainEvents(this, events);

    }


    /**
     * 退款订单状态校验
     */
    private void refundTradeStatusCheck() {
        if (!TradeStatusEnum.TRADE_SUCCESS.equals(tradeStatus))
            throw new UnsupportedOperationException("订单状态不合法: " + tradeStatus);
    }

    /**
     * 退款金额校验
     */
    private void refundAmountCheck(String refundAmount) {
        BigDecimal totalAmountD = new BigDecimal(this.paymentInfo.getPaymentAmount());
        BigDecimal refundD = new BigDecimal(totalRefundAmount != null ? totalRefundAmount : "0.0");
        //退款金额超限
        if (totalAmountD.subtract(refundD).subtract(new BigDecimal(refundAmount)).doubleValue() <= 0.0) {
            throw new IllegalArgumentException("refundAmount is " + refundAmount);
        }
    }

    /**
     * 订单状态机校验
     */
    private void tradeStatusCheck(Class<OrderDomainEvent> ec) {

//        if (ec.) {
//
//        }

        switch (tradeStatus) {
            case TRADE_SUCCESS:
                break;
            case TRADE_REFUND:
                break;
            case TRADE_CLOSED:
                break;
            case TRADE_FAILED:
                break;


        }


    }

}
