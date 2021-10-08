package org.ssh.boot.api.dto;

import lombok.Data;

/**
 * Created on 2021/9/30.
 * <p>
 * 付款完成之后需要补充的信息
 *
 * @author yzm
 */
@Data
public class OrderPaidDTO {

    private String tradeNo;

    private String gmtPayment;
}
