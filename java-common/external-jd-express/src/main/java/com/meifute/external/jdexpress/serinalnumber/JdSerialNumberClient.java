package com.meifute.external.jdexpress.serinalnumber;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.ECLP.EclpOpenService.response.queryPageSerialByBillNo.SerialNumber;
import com.jd.open.api.sdk.request.ECLP.EclpSerialQueryPageSerialByBillNoRequest;
import com.jd.open.api.sdk.response.ECLP.EclpSerialQueryPageSerialByBillNoResponse;

import java.util.List;
import java.util.Objects;

/**
 * @Classname JdSerialNumberClient
 * @Description
 * @Date 2020-07-23 18:43
 * @Created by MR. Xb.Wu
 */
public class JdSerialNumberClient {

    private static final byte BILL_TYPE = 24;
    private static final byte QUERY_TYPE = 1;

    private JdClient client;
    private JdSerialNumberRequest jdSerialNumberRequest;

    public JdSerialNumberClient(JdClient client, JdSerialNumberRequest jdSerialNumberRequest) {
        this.client = client;
        this.jdSerialNumberRequest = jdSerialNumberRequest;
    }

    /**
     * 查询序列号
     *
     * @return
     */
    public JdSerialNumberResponse execute() {
        EclpSerialQueryPageSerialByBillNoRequest request = new EclpSerialQueryPageSerialByBillNoRequest();
        request.setBillNo(jdSerialNumberRequest.getEclpSoNo());
        request.setBillType(BILL_TYPE);
        request.setPageNo(jdSerialNumberRequest.getCurrentPage());
        request.setPageSize(jdSerialNumberRequest.getPageSize());
        request.setQueryType(QUERY_TYPE);
        EclpSerialQueryPageSerialByBillNoResponse response;
        JdSerialNumberResponse jdSerialNumberResponse = new JdSerialNumberResponse();
        try {
            response = client.execute(request);
            if (Objects.isNull(response) || Objects.isNull(response.getQuerypageserialbybillnoResult())) {
                return jdSerialNumberResponse;
            }
            List<SerialNumber> serialNumbers = response.getQuerypageserialbybillnoResult().getSerialNumbers();
            if (serialNumbers == null || serialNumbers.isEmpty()) {
                return jdSerialNumberResponse;
            }
            List<SerialNumber> numberList = response.getQuerypageserialbybillnoResult().getSerialNumbers();
            jdSerialNumberResponse.setSerialNumbers(numberList);
        } catch (Exception e) {
            return null;
        }
        return jdSerialNumberResponse;
    }
}
