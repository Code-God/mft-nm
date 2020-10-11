package com.meifute.external.jdexpress.serinalnumber;

import com.jd.open.api.sdk.domain.ECLP.EclpOpenService.response.queryPageSerialByBillNo.SerialNumber;
import com.meifute.external.jdexpress.AbstractResponse;

import java.util.List;

/**
 * @Classname JdSerialNumberResponse
 * @Description
 * @Date 2020-07-23 18:43
 * @Created by MR. Xb.Wu
 */
public class JdSerialNumberResponse extends AbstractResponse {

    List<SerialNumber> serialNumbers;

    public List<SerialNumber> getSerialNumbers() {
        return serialNumbers;
    }

    public void setSerialNumbers(List<SerialNumber> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    @Override
    public String toString() {
        return "JdSerialNumberResponse{" +
                "serialNumbers=" + serialNumbers +
                '}';
    }
}
