package com.meifute.external.pay.wechat;

import com.meifute.external.pay.wechat.query.QueryWeChatOrderRequest;
import com.meifute.external.pay.wechat.unified.UnifiedWeChatPayRequest;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.*;

/**
 * @Classname CommonUtil
 * @Description
 * @Date 2020-07-29 10:28
 * @Created by MR. Xb.Wu
 */
@Slf4j
public class CommonUtil {

    public static final int RANDOM_STR_LENGTH = 32;

    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static final String GATEWAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private static final String BASE = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";


    /**
     * 生产指定长度的随机字符串
     *
     * @param length 表示生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE.length());
            sb.append(BASE.charAt(number));
        }
        return sb.toString();
    }

    // 生成签名
    public static String createSign(String apiKey, SortedMap<String, Object> parameters) {
        StringBuilder sb = new StringBuilder();

        parameters.forEach((k, v) -> {
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        });
        sb.append("key=").append(apiKey);
        return md5Encode(sb.toString(), "UTF-8").toUpperCase();
    }

    //加密
    private static String md5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(origin.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(origin.getBytes(charsetName)));
            }
        } catch (Exception ignored) {
            log.info("");
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte value : b) resultSb.append(byteToHexString(value));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static String getLocalIP() {
        List<String> ipList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            log.info("获取终端ip异常:{}", e.getMessage());
        }
        if (ipList.size() == 0) {
            ipList.add("127.0.0.1");
        }
        return ipList.get(0);
    }

    // 请求xml组装
    public static String getRequestXml(SortedMap<String, Object> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        parameters.forEach((k, v) -> {
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)
                    || "sign".equalsIgnoreCase(k)) {
                sb.append("<").append(k).append(">").append("<![CDATA[").append(v).append("]]></").append(k).append(">");
            } else {
                sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
            }
        });
        sb.append("</xml>");
        return sb.toString();
    }


    /**
     * xml 转 Map
     *
     * @param xml
     * @return
     */
    public static SortedMap<String, String> dom2Map(String xml) {
        SortedMap<String, String> map = new TreeMap<>();
        Document doc = null;
        Element root = null;
        try {
            doc = DocumentHelper.parseText(xml);
            if (doc == null) {
                return map;
            }
            root = doc.getRootElement();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
                Element e = (Element) iterator.next();
                map.put(e.getName().trim(), e.getText());
            }
        } catch (DocumentException ignored) {
        } finally {
            if (doc != null) {
                doc.clearContent();
            }
            if (root != null) {
                root.clearContent();
            }
        }
        return map;
    }


    public static boolean verifySign(String apiKey, SortedMap<String, String> map) {
        String charset = "utf-8";
        String signFromAPIResponse = map.get("sign");
        if (signFromAPIResponse == null || signFromAPIResponse.isEmpty()) {
            log.error("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                v = v.trim();
                sb.append(k).append("=").append(v).append("&");
            }
        });
        sb.append("key=").append(apiKey);
        String resultSign = md5Encode(sb.toString(), charset).toUpperCase();
        String temPaySign = map.get("sign").toUpperCase();
        return temPaySign.equals(resultSign);
    }


    public static boolean checkPayResult(String returnCode, String resultCode) {
        var result = false;
        if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
            result = true;
        }
        return result;
    }

    public static boolean checkPayResult(String tradeState) {
        var result = false;
        if ("SUCCESS".equals(tradeState)) {
            result = true;
        }
        return result;
    }

    public static boolean checkPayResult(SortedMap<String, String> resultMap) {
        var result = false;
        if (checkPayResult(resultMap.get("trade_state")) && checkPayResult(resultMap.get("return_code"), resultMap.get("result_code"))) {
            result = true;
        }
        return result;
    }




}
