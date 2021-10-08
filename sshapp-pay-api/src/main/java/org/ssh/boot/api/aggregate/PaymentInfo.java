package org.ssh.boot.api.aggregate;

import lombok.Data;

/**
 * Created on 2021/9/30.
 *
 * @author yzm
 */
@Data
public class PaymentInfo {

    private String tradeNo;

    private String outTradeNo;

    private String subject;

    private String timeExpire;

    private String gmtPayment;

    private String paymentAmount;


}
