package com.meifute.external.pay.alipay;

import com.meifute.external.pay.alipay.query.AliPayQueryOrderRequest;
import com.meifute.external.pay.alipay.query.AliPayQueryOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Classname MftAliPayDefaultClientTest
 * @Description
 * @Date 2020-07-30 18:08
 * @Created by MR. Xb.Wu
 */
@Slf4j
class MftAliPayDefaultClientTest {

    @Test
    void execute() {

        MftAliPayClient client = new MftAliPayDefaultClient("2019072665935835", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDinqTRR1PTk5dH VaWC8+UDzOu54AoIvEIlBxjcEcit9twantm0embVRkOU777VWguFHpzx/aOyBfja N27ZM6/PtThWsUg+SkpftHJ/Ycw9kOt7kXhGvyC8Prf/Spb+iTHxpRK61Ghj8jKZ dKMuNX7IjTraY580JxXwaWTNhwtsP+nNuoE/sYkzEPMSgjqSt8VMMZKVQKb0asAh s4xnoEWrDaaUD75440Vg65zLAVIQm7YxT0YQTCBWSDlz7NwPDLy5XZT4qEvoyPNH YtGKM8/gZnKj5r54ky/v9cL98c0kAYkV7QUDH8Mp2VGsHNxlJbIh6bFvedRCxkb5 VTmXHxbDAgMBAAECggEAJCRQ/N93SwL62XGbV+s11wlTfu1wLDx6ABXFrTNY4gXA zK4Q4muzAsiV9qbVtIkL1UswlJdzB+1T5tl4YEOSAnpQZeMYNnb6rZDFwfMC876t D6P/nqjUu7D0/04SDr4qrPM6rXk6vNXfpAGqFLCLa8i6XiINPfAN262zWePzumua M7Z/8KTycr8YTd2tPxMfTP1x5Q2rJQabDoT349Cw+HcWAcjbt48vQu7k5xzzcwzv /dM37KCPIIwH1zvj35gO/GkHcVd6/Ll74c6+kDVJVfnyfxJ8NWf4CVhipEMkPhWq LgzEDjWymn1Kxwq8uuJXPDM4DEUWz6DatFYfYEI0QQKBgQD0wxqNbElJrk0g9nAR p+i+nl5tutFS90d/i5BKq8VheenGsBcjdXjbPMX8LzmRcoA0lemwNuaYUXkTln99 LXm5lHgjZv2p7sprBPjG2XZXmgxUX8sCu63yrrcuQN1h8PHT2tfWP57PBGKsj5GO sYQ7uUFEAxNFtFHkV+0BqsBKKwKBgQDtBkv/zKAdPGyNHTK1Ss/ZguUZn5CJHxdM N7FBtvE1rRKrQiZSTuF6pYPIPOX2gIKxpLk11zq5RX25VJFACF9qfiXpn80DQeTZ TKMNXHz3dcmyRrrCTfy3ltyC/WdKWVJSe3nYkt3orde0MOKaFHMR7PUiGIJOK+c/ uTGnv6gRyQKBgQCuWQfh81+lD3a7Yy/jM8LzfWXwLeILgj842FZZClBYJfpjYG2r AXOsX3b8MzuDopc2Xg8PSZwqpeZkEJERvsH1co+9UBCi6ojfgLQhGShWMFJWC8JV WREITHIJjYTkzjfEXB/g6oKaHhqXptDdXw+9ZzHZW4Nv5Ils6UDXLiU47wKBgQDo EspG42vCNG90m6oSp/Vq2ybGN92k5LRDBCSiWyVBkPw+gr5SyLktBPJLFJV1Dc7p cIeHl/z/enLnC05Mi+YgtvMYFEb/cX1I5iif5HbXNJUP4QDrTsCqYSP3fXWs7lgU OqHK08JDGDOjUloM0wj3/DLn92decs4xLnrX7Gf7QQKBgCGGZIL7ZEt6w1hoFyaS 5DrEdkrT2QGtnixMGc0mopahxDm4KIRSqekoziw4v2DzxYUsYGoEqUTyZ94LkIEd Ab0bzq46BkGYP5mzfJP5zIGPQDxnLm8cdsONYnmJdesuUgDC4TV99sWaU4Zxcp9X tdqb4jzv/PoW/N4ksam9twDm",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy3JpjzWQv+3JnqSS9Zk/aM8qgUEXIp5bW8/d/4TSXZlQon1r7+i1R86CQdh9e/MMMIUxFXUhenp9rfg4lKFT4zC6mld5fprQuM+eB7hXQgwnzw87AsgLuQ5Bn5cGZwngETGlwQvv9uHO+ypt0BUGIDqomLpthDcTwm9+/fPwphJRLaFW3rK6Lws5OYCdTR8iNM3Ni0c97hPLKbcLD4OZ1ly+Q+oeC/sxwixojhB0urCoCW/VjhJ0Gm/ERhigEoXOTJlHpcYS3kCJotzWD9yIIPba6SYaUbxd2RIHShkGExlA67DRiE+e1jYzxLj50q07/WNQNIEHqcM2ouPCdfSPDwIDAQAB");
        AliPayQueryOrderRequest request = new AliPayQueryOrderRequest();
        request.setOutTradeNo("1056689534404005888");
        AliPayQueryOrderResponse response = client.execute(request);
        log.info("" + response);
    }
}