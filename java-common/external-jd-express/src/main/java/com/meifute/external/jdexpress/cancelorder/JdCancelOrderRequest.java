package com.meifute.external.jdexpress.cancelorder;

import com.meifute.external.jdexpress.AbstractRequest;
import com.meifute.external.jdexpress.JdRequest;


/**
 * @Classname JdCancelOrderRequest
 * @Description
 * @Date 2020-07-23 18:01
 * @Created by MR. Xb.Wu
 */
public class JdCancelOrderRequest extends AbstractRequest implements JdRequest<JdCancelOrderResponse> {

    private String eclpSoNo;

    public JdCancelOrderRequest(){}

    public JdCancelOrderRequest(String eclpSoNo) {
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

        public JdCancelOrderRequest build(){
            return new JdCancelOrderRequest(eclpSoNo);
        }
    }

    public String getEclpSoNo() {
        return eclpSoNo;
    }

    public void setEclpSoNo(String eclpSoNo) {
        this.eclpSoNo = eclpSoNo;
    }
}
