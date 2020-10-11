package com.meifute.external.jdexpress.cancelorder;

import com.meifute.external.jdexpress.AbstractResponse;

/**
 * @Classname JdCancelOrderResponse
 * @Description
 * @Date 2020-07-23 18:02
 * @Created by MR. Xb.Wu
 */
public class JdCancelOrderResponse extends AbstractResponse {

    private boolean result = false;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JdCancelOrderResponse{" +
                "result=" + result +
                '}';
    }
}
