package com.meifute.external.jdexpress.push;

import com.meifute.external.jdexpress.AbstractResponse;

/**
 * @Classname JdExpressPushResponse
 * @Description
 * @Date 2020-07-23 17:24
 * @Created by MR. Xb.Wu
 */
public class JdExpressPushResponse extends AbstractResponse {

    private String eclpSoNo;

    private String msg;

    public String getEclpSoNo() {
        return eclpSoNo;
    }

    public void setEclpSoNo(String eclpSoNo) {
        this.eclpSoNo = eclpSoNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JdExpressPushResponse{" +
                "eclpSoNo='" + eclpSoNo + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
