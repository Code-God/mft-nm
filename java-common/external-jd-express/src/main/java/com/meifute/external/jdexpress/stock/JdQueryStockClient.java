package com.meifute.external.jdexpress.stock;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.ECLP.EclpStockQueryStockRequest;
import com.jd.open.api.sdk.response.ECLP.EclpStockQueryStockResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Classname JdQueryStockClient
 * @Description
 * @Date 2020-07-23 18:12
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class JdQueryStockClient {

    private static final String STOCK_TYPE = "1";
    private static final String STOCK_STATUS = "1";
    private static final int RETURN_ZERO_STOCK = 1;

    private JdClient client;
    private JdQueryStockRequest jdQueryStockRequest;

    public JdQueryStockClient(JdClient client, JdQueryStockRequest jdQueryStockRequest) {
        this.client = client;
        this.jdQueryStockRequest = jdQueryStockRequest;
    }


    /**
     * 查询库存信息
     *
     * @return
     */
    public JdQueryStockResponse execute() {
        EclpStockQueryStockRequest request = new EclpStockQueryStockRequest();
        request.setDeptNo(jdQueryStockRequest.getDepartmentNo());
        request.setWarehouseNo(jdQueryStockRequest.getWarehouseNo());
        request.setStockStatus(STOCK_STATUS);
        request.setStockType(STOCK_TYPE);
        request.setGoodsNo(jdQueryStockRequest.getGoodsNo());
        request.setCurrentPage(jdQueryStockRequest.getCurrentPage());
        request.setPageSize(jdQueryStockRequest.getPageSize());
        request.setReturnZeroStock(RETURN_ZERO_STOCK);
        EclpStockQueryStockResponse response;
       JdQueryStockResponse jdQueryStockResponse = new JdQueryStockResponse();
        try {
            response = client.execute(request);
            if (Objects.isNull(response) || Objects.isNull(response.getQuerystockResult())) {
                return jdQueryStockResponse;
            }
            List<JdQueryStock> stocks = new ArrayList<>();
            response.getQuerystockResult().forEach(p ->{
                JdQueryStock jdQueryStock = new JdQueryStock();
                jdQueryStock.setTransportGoodsNo(p.getGoodsNo()[0]);
                jdQueryStock.setTotalAmount(p.getTotalNum()[0]);
                stocks.add(jdQueryStock);
            });
            jdQueryStockResponse.setStocks(stocks);
        } catch (Exception e) {
            log.error("queryJdStock error:{}", e.getMessage());
        }
        return jdQueryStockResponse;
    }
}
