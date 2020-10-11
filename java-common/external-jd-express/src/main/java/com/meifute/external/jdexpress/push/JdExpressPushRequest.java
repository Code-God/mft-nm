package com.meifute.external.jdexpress.push;

import com.meifute.external.jdexpress.AbstractRequest;
import com.meifute.external.jdexpress.JdRequest;



/**
 * @Classname JdExpressPushRequest
 * @Description
 * @Date 2020-07-23 16:30
 * @Created by MR. Xb.Wu
 */
public class JdExpressPushRequest extends AbstractRequest implements JdRequest<JdExpressPushResponse> {

    private String warehouseNo;
    private String departmentNo;
    private String shopNo;
    private String isvSource;
    private String salePlatformSource;
    private String orderMark;
    private String orderId;
    private String transportGoodsNo;
    private String goodsAmount;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;

    public JdExpressPushRequest() {}

    public JdExpressPushRequest(String warehouseNo, String departmentNo,
                         String shopNo, String isvSource, String salePlatformSource, String orderMark, String orderId,
                         String transportGoodsNo, String goodsAmount, String recipientName, String recipientPhone, String recipientAddress) {
        this.warehouseNo = warehouseNo;
        this.departmentNo = departmentNo;
        this.shopNo = shopNo;
        this.isvSource = isvSource;
        this.salePlatformSource = salePlatformSource;
        this.orderMark = orderMark;
        this.orderId = orderId;
        this.transportGoodsNo = transportGoodsNo;
        this.goodsAmount = goodsAmount;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.recipientAddress = recipientAddress;
    }


    /**
     * Builder模式
     *
     * @return
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String warehouseNo = null;
        private String departmentNo = null;
        private String shopNo = null;
        private String isvSource = null;
        private String salePlatformSource = null;
        private String orderMark = null;
        private String orderId = null;
        private String transportGoodsNo = null;
        private String goodsAmount = null;
        private String recipientName = null;
        private String recipientPhone = null;
        private String recipientAddress = null;


        public Builder warehouseNo(String warehouseNo) {
            this.warehouseNo = warehouseNo;
            return this;
        }

        public Builder departmentNo(String departmentNo) {
            this.departmentNo = departmentNo;
            return this;
        }

        public Builder shopNo(String shopNo) {
            this.shopNo = shopNo;
            return this;
        }

        public Builder isvSource(String isvSource) {
            this.isvSource = isvSource;
            return this;
        }

        public Builder salePlatformSource(String salePlatformSource) {
            this.salePlatformSource = salePlatformSource;
            return this;
        }

        public Builder orderMark(String orderMark) {
            this.orderMark = orderMark;
            return this;
        }

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder transportGoodsNo(String transportGoodsNo) {
            this.transportGoodsNo = transportGoodsNo;
            return this;
        }

        public Builder goodsAmount(String goodsAmount) {
            this.goodsAmount = goodsAmount;
            return this;
        }

        public Builder recipientName(String recipientName) {
            this.recipientName = recipientName;
            return this;
        }

        public Builder recipientPhone(String recipientPhone) {
            this.recipientPhone = recipientPhone;
            return this;
        }

        public Builder recipientAddress(String recipientAddress) {
            this.recipientAddress = recipientAddress;
            return this;
        }

        public JdExpressPushRequest build() {
            return new JdExpressPushRequest(warehouseNo, departmentNo,
                    shopNo, isvSource, salePlatformSource, orderMark, orderId, transportGoodsNo,
                    goodsAmount, recipientName, recipientPhone, recipientAddress);
        }
    }


    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getIsvSource() {
        return isvSource;
    }

    public void setIsvSource(String isvSource) {
        this.isvSource = isvSource;
    }

    public String getSalePlatformSource() {
        return salePlatformSource;
    }

    public void setSalePlatformSource(String salePlatformSource) {
        this.salePlatformSource = salePlatformSource;
    }

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransportGoodsNo() {
        return transportGoodsNo;
    }

    public void setTransportGoodsNo(String transportGoodsNo) {
        this.transportGoodsNo = transportGoodsNo;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }
}
