package com.meifute.external.jdexpress.stock;

import com.meifute.external.jdexpress.AbstractResponse;

import java.util.List;

/**
 * @Classname JdQueryStockResponse
 * @Description
 * @Date 2020-07-23 18:12
 * @Created by MR. Xb.Wu
 */
public class JdQueryStockResponse extends AbstractResponse {

    List<JdQueryStock> stocks;

    public List<JdQueryStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<JdQueryStock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "JdQueryStockResponse{" +
                "stocks=" + stocks +
                '}';
    }
}
