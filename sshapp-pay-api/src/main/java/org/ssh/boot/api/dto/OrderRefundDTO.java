package org.ssh.boot.api.dto;

import lombok.Data;

/**
 * Created on 2021/9/30.
 *
 * @author yzm
 */
@Data
public class OrderRefundDTO {

    private String outTradeNo;

    private String refundNo;

    private String outRefundNo;

    private String refundAmount;

    private String refundReason;

    private String gmtRefundPay;


}
