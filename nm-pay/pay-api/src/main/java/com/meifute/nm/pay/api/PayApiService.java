package com.meifute.nm.pay.api;

import com.meifute.nm.pay.api.account.AccountApiService;
import com.meifute.nm.pay.api.pay.TradePayApiService;
import com.meifute.nm.pay.api.journal.JournalApiService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname PayApiService
 * @Description
 * @Date 2020-08-10 12:01
 * @Created by MR. Xb.Wu
 */
@Api(tags = "支付模块内部统一接口")
public interface PayApiService extends AccountApiService, TradePayApiService, JournalApiService {

}
