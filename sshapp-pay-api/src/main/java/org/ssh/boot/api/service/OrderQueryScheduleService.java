package org.ssh.boot.api.service;

import com.yzm.schedule.api.FutureTaskResult;
import com.yzm.schedule.api.JDKScheduleExecutor;
import com.yzm.schedule.api.RetryTask;
import com.yzm.schedule.api.ScheduleExecutor;
import io.github.halo.pay.api.PayApi;
import io.github.halo.pay.api.PayApiResp;
import io.github.halo.pay.api.param.OrderQueryParam;
import io.github.halo.pay.api.resp.OrderQueryResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssh.boot.api.aggregate.Order;
import org.ssh.boot.api.dto.OrderPaidDTO;
import org.ssh.boot.api.enums.TradeStatusEnum;
import org.ssh.boot.api.event.OrderDomainEvent;
import org.ssh.boot.api.event.ResultWithDomainEvents;
import org.ssh.boot.api.publisher.OrderDomainEventPublisher;
import org.ssh.boot.api.repository.OrderRepository;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2021/10/8.
 *
 * @author yzm
 */
@Component
public class OrderQueryScheduleService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDomainEventPublisher orderDomainEventPublisher;

    private ScheduleExecutor executor = new JDKScheduleExecutor();

    public RetryTask submit(String outTradeNo, PayApi payApi) {
        return new RetryTask<FutureTaskResult>() {
            @Override
            public long[] delayTimes() {
                return new long[]{5, 10, 15, 30, 60, 80, 110, 300};
            }

            @Override
            public TimeUnit timeUnit() {
                return TimeUnit.SECONDS;
            }

            @Override
            public int dtIndex() {
                return 0;
            }

            @Override
            public Object attach() {
                return null;
            }

            @Override
            public FutureTaskResult call() throws Exception {
                PayApiResp<OrderQueryResp> apiResp = payApi.query(new OrderQueryParam<PayApiResp>() {
                    @Override
                    public String tradeNo() {
                        return null;
                    }

                    @Override
                    public String outTradeNo() {
                        return outTradeNo;
                    }
                });

                if (TradeStatusEnum.TRADE_SUCCESS.name().equals(apiResp.data().tradeStatus())) {
                    Order order = orderRepository.findByOutTradeNo(outTradeNo);
                    ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents = order.paid(new OrderPaidDTO(apiResp.data().tradeNo(), apiResp.data().gmtPayment()));
                    orderRepository.save(orderAndEvents.result());
                    orderDomainEventPublisher.publish(orderAndEvents.result(), orderAndEvents.events());
                    return buildResult(true, orderAndEvents.result());
                } else {
                    return buildResult(false, null);
                }
            }
        };
    }


    private FutureTaskResult buildResult(boolean success, Object data) {
        return new FutureTaskResult() {
            @Override
            public boolean success() {
                return success;
            }

            @Override
            public Object data() {
                return data;
            }

        };
    }

}
