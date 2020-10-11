package com.meifute.external.pay.icbc.notify;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname IcbcResultBizContent
 * @Description TODO
 * @Date 2020-05-22 14:16
 * @Created by MR. Xb.Wu
 */
@Data
public class IcbcResultBizContent implements Serializable {

    private String return_code;

    private String order_id;

    private String msg_id;

    private String payment_amt;

    private String out_trade_no;

    private String attach;
}
