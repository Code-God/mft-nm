package com.meifute.nm.nmcommon.utils;

import java.util.Random;

/**
 * @Auther: wxb
 * @Date: 2018/7/30 10:01
 * @Auto: I AM A CODE MAN -_-!
 * @Description: id生成策略
 */
public class IDUtils {

    private static IdWorker idWorker;


    public static long genLongId() {
        if(idWorker == null) {
            idWorker = new IdWorker();
        }
        return idWorker.nextId();
    }

    /**
     * 生成id (64位，19位长度)
     */
    public static String genId() {
        if(idWorker == null) {
            idWorker = new IdWorker();
        }
        return String.valueOf(idWorker.nextId());
    }

    /**
     * 商品id生成 (64位，19位长度)
     */
    public static String genItemId() {
        if(idWorker == null) {
            idWorker = new IdWorker();
        }
        return String.valueOf(idWorker.nextId());
    }

    /**
     * 图片名生成 (64位，19位长度)
     */
    public static String genImageName() {
        if(idWorker == null) {
            idWorker = new IdWorker();
        }
        return String.valueOf(idWorker.nextId());
    }

    /**
     * 生成定长随机字母数字
     * @param length 长度
     * @return
     */
    public static String getStringRandom(int length) {

        StringBuilder val = new StringBuilder();
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * 随机id生成
     */
    public static String genRandomId() {
        //取当前时间的长整形值包含毫秒
        String millis = System.currentTimeMillis() + "";

        millis = millis.substring(5, millis.length());
        //加上四位随机数
        Random random = new Random();
        int end4 = random.nextInt(9999);
        //如果不足两位前面补0
        String str = String.format("%04d", end4);

        return millis + str;
    }

}
