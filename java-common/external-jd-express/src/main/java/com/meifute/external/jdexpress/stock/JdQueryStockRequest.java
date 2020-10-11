package com.meifute.external.jdexpress.stock;

import com.meifute.external.jdexpress.AbstractResponse;
import com.meifute.external.jdexpress.JdRequest;

/**
 * @Classname JdQueryStockRequest
 * @Description
 * @Date 2020-07-23 18:12
 * @Created by MR. Xb.Wu
 */
public class JdQueryStockRequest extends AbstractResponse implements JdRequest<JdQueryStockResponse> {

    private String goodsNo;
    private Integer currentPage;
    private Integer pageSize;
    private String departmentNo;
    private String warehouseNo;

    public JdQueryStockRequest(){}

    public JdQueryStockRequest(String goodsNo, Integer currentPage, Integer pageSize, String departmentNo, String warehouseNo) {
        this.goodsNo = goodsNo;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.departmentNo = departmentNo;
        this.warehouseNo = warehouseNo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String goodsNo = null;
        private Integer currentPage = null;
        private Integer pageSize = null;
        private String departmentNo = null;
        private String warehouseNo = null;

        public Builder goodsNo(String goodsNo) {
            this.goodsNo = goodsNo;
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
        public Builder departmentNo(String departmentNo) {
            this.departmentNo = departmentNo;
            return this;
        }
        public Builder warehouseNo(String warehouseNo) {
            this.warehouseNo = warehouseNo;
            return this;
        }
        public JdQueryStockRequest build(){
            return new JdQueryStockRequest(goodsNo,currentPage,pageSize,departmentNo,warehouseNo);
        }
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
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

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }
}
