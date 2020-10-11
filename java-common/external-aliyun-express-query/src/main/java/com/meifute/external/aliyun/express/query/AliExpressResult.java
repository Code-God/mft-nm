package com.meifute.external.aliyun.express.query;

import lombok.Data;

import java.util.List;

/**
 * Created by liuliang on 2019/3/18.
 */
@Data
public class AliExpressResult {

    private String reason;

    private String ico;

    private String phone;

    private Boolean success;

    private String nu;

    private List<MftExpressDetail> data;

    private String exname;

    private String company;

    private String url;

    private String status;

}
