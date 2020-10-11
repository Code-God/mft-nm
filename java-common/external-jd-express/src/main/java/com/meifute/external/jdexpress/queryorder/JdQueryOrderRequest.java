package com.meifute.external.jdexpress.queryorder;

import com.meifute.external.jdexpress.AbstractRequest;
import com.meifute.external.jdexpress.JdRequest;


/**
 * @Classname JdQueryWayBillRequest
 * @Description
 * @Date 2020-07-23 16:53
 * @Created by MR. Xb.Wu
 */
public class JdQueryOrderRequest extends AbstractRequest implements JdRequest<JdQueryOrderResponse> {

    private String eclpSoNo;

    public JdQueryOrderRequest(){}

    public JdQueryOrderRequest(String eclpSoNo) {
        this.eclpSoNo = eclpSoNo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String eclpSoNo = null;

        public Builder eclpSoNo(String eclpSoNo) {
            this.eclpSoNo = eclpSoNo;
            return this;
        }

        public JdQueryOrderRequest build(){
            return new JdQueryOrderRequest(eclpSoNo);
        }
    }

    public String getEclpSoNo() {
        return eclpSoNo;
    }

    public void setEclpSoNo(String eclpSoNo) {
        this.eclpSoNo = eclpSoNo;
    }

}
