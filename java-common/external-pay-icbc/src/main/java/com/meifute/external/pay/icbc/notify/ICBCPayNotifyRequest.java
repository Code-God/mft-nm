package com.meifute.external.pay.icbc.notify;

import com.meifute.external.pay.icbc.ICBCPayRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Classname ICBCPayNotifyRequest
 * @Description
 * @Date 2020-07-31 16:29
 * @Created by MR. Xb.Wu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ICBCPayNotifyRequest extends ICBCPayRequest<ICBCPayNotifyResponse> {

    //路径，就是requestMapping的值，用于签名使用
    private String requestMappingPath;

    //网关公钥
    private String publicKey;

    //工行回调时候带来的入参map
    private Map<String, String> map;

}
