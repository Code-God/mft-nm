package com.meifute.external.jdexpress.stock;

import java.io.Serializable;

/**
 * @Classname JdQueryStock
 * @Description
 * @Date 2020-07-23 18:35
 * @Created by MR. Xb.Wu
 */
public class JdQueryStock implements Serializable {

    private String transportGoodsNo;
    private Integer totalAmount;

    public String getTransportGoodsNo() {
        return transportGoodsNo;
    }

    public void setTransportGoodsNo(String transportGoodsNo) {
        this.transportGoodsNo = transportGoodsNo;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

}
