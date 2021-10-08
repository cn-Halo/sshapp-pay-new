package org.ssh.boot.api.dto;

import lombok.Data;

/**
 * Created on 2021/9/30.
 *
 * @author yzm
 */
@Data
public class OrderCreateDTO {
    private String outTradeNo;

    private String paymentAmount;

    private String subject;
}
