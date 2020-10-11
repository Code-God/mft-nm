package com.meifute.external.jdexpress.queryorder;

import com.meifute.external.jdexpress.AbstractResponse;

/**
 * @Classname JdQueryWayBillResponse
 * @Description
 * @Date 2020-07-23 16:54
 * @Created by MR. Xb.Wu
 */
public class JdQueryOrderResponse extends AbstractResponse {

    /**
     * 物流单号
     */
    private String wayBill;

    /**
     * 是否已签收
     */
    private boolean isSignFor = false;

    /**
     * 是否可以关闭
     */
    private boolean isCanCancel = false;

    public String getWayBill() {
        return wayBill;
    }

    public void setWayBill(String wayBill) {
        this.wayBill = wayBill;
    }

    public boolean isSignFor() {
        return isSignFor;
    }

    public void setSignFor(boolean signFor) {
        isSignFor = signFor;
    }

    public boolean isCanCancel() {
        return isCanCancel;
    }

    public void setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
    }

    @Override
    public String toString() {
        return "JdQueryOrderResponse{" +
                "wayBill='" + wayBill + '\'' +
                ", isSignFor=" + isSignFor +
                ", isCanCancel=" + isCanCancel +
                '}';
    }
}
