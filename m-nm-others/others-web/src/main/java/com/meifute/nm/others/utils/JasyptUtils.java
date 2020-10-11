package com.meifute.nm.others.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @Classname JasyptUtils
 * @Description
 * @Date 2020-07-07 13:06
 * @Created by MR. Xb.Wu
 */
public class JasyptUtils {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("G0CvDz7oJn6");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("dev_client_001");
        String password = textEncryptor.encrypt("meifute@123");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }
}
