package com.meifute.nm.othersserver.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: wuxb
 * @Date: 2019-03-18 12:54
 * @Auto: I AM A CODE MAN -_-!
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedWeChatDto implements Serializable {

    private String appId;
    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timestamp;
    private String sign;
    private String packAge;
}
