package com.meifute.nm.others.utils;

import com.meifute.nm.others.business.youxin.config.Config;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @Classname SignUtil
 * @Description TODO
 * @Date 2020-06-30 10:29
 * @Created by MR. Xb.Wu
 */
public class SignUtil {

    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;//设置长度
    private static final String PUBLIC_KEY_NAME = "publicKey";
    private static final String PRIVATE_KEY_NAME = "privateKey";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String ENCODE_ALGORITHM = "SHA-256";

    /**
     * 生成公、私钥
     * 根据需要返回String或byte[]类型
     *
     * @return
     */
    private static Map<String, String> createRSAKeys() {
        Map<String, String> keyPairMap = new HashMap<String, String>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            /*Map<String, byte[]> byteMap = new HashMap<String, byte[]>();
            byteMap.put(PUBLIC_KEY_NAME, publicKey.getEncoded());
            byteMap.put(PRIVATE_KEY_NAME, privateKey.getEncoded());*/

            //获取公、私钥值
            String publicKeyValue = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyValue = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            //存入
            keyPairMap.put(PUBLIC_KEY_NAME, publicKeyValue);
            keyPairMap.put(PRIVATE_KEY_NAME, privateKeyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keyPairMap;
    }

    /**
     * 解码PublicKey
     *
     * @param key
     * @return
     */
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码PrivateKey
     *
     * @param key
     * @return
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 签名
     *
     * @param key         私钥
     * @param requestData 请求参数
     * @return
     */
    public static String sign(String key, String requestData) {
        String signature = null;
        byte[] signed = null;
        try {
            PrivateKey privateKey = getPrivateKey(key);

            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            Sign.initSign(privateKey);
            Sign.update(requestData.getBytes());
            signed = Sign.sign();

            signature = Base64.getEncoder().encodeToString(signed);
            System.out.println("===签名结果：" + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    /**
     * 验签
     *
     * @param key         公钥
     * @param requestData 请求参数
     * @param signature   签名
     * @return
     */
    public static boolean verifySign(String key, String requestData, String signature) {
        boolean verifySignSuccess = false;
        try {
            PublicKey publicKey = getPublicKey(key);

            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(publicKey);
            verifySign.update(requestData.getBytes());

            verifySignSuccess = verifySign.verify(Base64.getDecoder().decode(signature));
            System.out.println("===验签结果：" + verifySignSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifySignSuccess;
    }

    // 生成签名
    public static String createSign(String apiKey, String characterEncoding,
                                    SortedMap<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + apiKey);
        System.out.println(sb.toString());
        String sign = MD5Encode(sb.toString(), characterEncoding)
                .toUpperCase();
        return sign;
    }

    public static String createRequestDate(String... parameters) {
        StringBuffer sb = new StringBuffer();
//        Set es = parameters.entrySet();
//        Iterator it = es.iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            String k = (String) entry.getKey();
//            Object v = entry.getValue();
//            if (null != v && !"".equals(v) && !"sign".equals(k)
//                    && !"key".equals(k)) {
//                sb.append(k + "=" + v + "&");
//            }
//        }
//        sb.append("key=" + apiKey);
//        System.out.println(sb.toString());
//        String sign = MD5Encode(sb.toString(), characterEncoding)
//                .toUpperCase();
        return sb.toString();
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        }
        catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


    public static void main(String[] args) {
//        Map<String, String> keyPairMap = createRSAKeys();
//        System.out.println("生成公、私钥测试："+keyPairMap);
//
//        String publicKey = keyPairMap.get(PUBLIC_KEY_NAME);
//        String privateKey = keyPairMap.get(PRIVATE_KEY_NAME);

        String publicKey = Config.YOUXIN_PUBLIC_KEY;
        String privateKey = Config.YOUXIN_PRIVATE_KEY;
        System.out.println("===开始RSA公、私钥测试===");
        String str = "seqid=1&childIdentify=13917280652";
//        String str = "seqid=1&phone=13764348378";
//        String str = "seqid=1&orderCode=7&buyerPhone=13917504631";
//        String str = "seqid=1&orderCode=7";
        String sign = sign(privateKey, str);

        verifySign(publicKey, str, sign);
    }
}
