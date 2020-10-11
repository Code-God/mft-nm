package com.meifute.external.jdexpress.serinalnumber;

import com.meifute.external.jdexpress.AbstractRequest;
import com.meifute.external.jdexpress.JdRequest;

/**
 * @Classname JdSerialNumberRequest
 * @Description
 * @Date 2020-07-23 18:42
 * @Created by MR. Xb.Wu
 */
public class JdSerialNumberRequest extends AbstractRequest implements JdRequest<JdSerialNumberResponse> {

    private String eclpSoNo;
    private Integer currentPage;
    private Integer pageSize;

    public JdSerialNumberRequest(){}

    public JdSerialNumberRequest(String eclpSoNo, Integer currentPage, Integer pageSize) {
        this.eclpSoNo = eclpSoNo;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String eclpSoNo = null;
        private Integer currentPage = null;
        private Integer pageSize = null;

        public Builder eclpSoNo(String eclpSoNo) {
            this.eclpSoNo = eclpSoNo;
            return this;
        }
        public Builder currentPage(Integer currentPage) {
            this.currentPage = currentPage;
            return this;
        }
        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public JdSerialNumberRequest build(){
            return new JdSerialNumberRequest(eclpSoNo,currentPage,pageSize);
        }
    }

    public String getEclpSoNo() {
        return eclpSoNo;
    }

    public void setEclpSoNo(String eclpSoNo) {
        this.eclpSoNo = eclpSoNo;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
