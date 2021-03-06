package org.ssh.boot.api.aggregate;

import lombok.Data;
import org.ssh.boot.api.enums.TradeStatusEnum;

/**
 * @author yzm
 * @date 2021/9/29 22:01
 */
@Data
public class RefundInfo {

    private String outTradeNo;

    private String refundNo;

    private String outRefundNo;

    private String refundAmount;

    private String refundReason;

    private String gmtRefundPay;

    private TradeStatusEnum tradeStatus;


}
