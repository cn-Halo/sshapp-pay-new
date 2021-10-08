package org.ssh.boot.api.service;

import io.github.halo.pay.api.PayApi;
import io.github.halo.pay.api.PayApiResp;
import io.github.halo.pay.api.param.InParam;
import io.github.halo.pay.api.resp.FacePayResp;
import io.github.halo.pay.api.resp.PayResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssh.boot.api.aggregate.Order;
import org.ssh.boot.api.dto.OrderCreateDTO;
import org.ssh.boot.api.dto.OrderPaidDTO;
import org.ssh.boot.api.event.OrderDomainEvent;
import org.ssh.boot.api.event.ResultWithDomainEvents;
import org.ssh.boot.api.exception.OrderNotFoundException;
import org.ssh.boot.api.exception.PayApiException;
import org.ssh.boot.api.publisher.OrderDomainEventPublisher;
import org.ssh.boot.api.repository.OrderRepository;

/**
 * @author yzm
 * @date 2021/9/27 22:35
 */
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDomainEventPublisher orderDomainEventPublisher;


    private OrderQueryScheduleService orderQueryScheduleService;


    public Order createOrder(OrderCreateDTO dto) {
        ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents = Order.createOrder(dto);
        Order order = orderAndEvents.result();
        orderRepository.save(order);//保存数据库
        orderDomainEventPublisher.publish(order, orderAndEvents.events());//发布领域事件
        //创建saga。。。。。
        return order;
    }


    public void payOrder(Long orderId, PayApi payApi, InParam<PayApiResp<PayResp>> inParam) throws Exception {
        Order order = orderRepository.findById(orderId);
        if (order == null)
            throw new OrderNotFoundException(orderId + "");
        //创建saga


        //调用支付api
        PayApiResp<PayResp> payApiResp = payApi.pay(inParam);
        PayResp payResp = payApiResp.data();
        String tradeNo = null;
        String gmtPayment = null;
        if (!payApiResp.isSuccess()) {
            throw new PayApiException(payApiResp.msg() + payApiResp.subMsg());
        }
        if (payResp instanceof FacePayResp) {
            gmtPayment = ((FacePayResp) payResp).gmtPayment();
            tradeNo = ((FacePayResp) payResp).tradeNo();
        }
        ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents = order.paid(new OrderPaidDTO(tradeNo, gmtPayment));
        orderRepository.save(orderAndEvents.result());
        orderDomainEventPublisher.publish(orderAndEvents.result(), orderAndEvents.events());
        //todo 当是等待支付的时候 发起定时查询
        orderQueryScheduleService.submit(order.getPaymentInfo().getOutTradeNo(), payApi);

    }


}
