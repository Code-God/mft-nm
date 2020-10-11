package com.meifute.core.tool;

import com.meifute.core.lang.MftException;
import com.meifute.core.lang.MftStrings;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wzeng
 * @date 2020/8/7
 * @Description
 */
class MftEncryptTest {
    String str="abc中国人";
    @Test
    void base64Encode() {

        String s=MftStrings.toUtf8String(MftEncrypt.base64Encode(MftStrings.toUtf8Bytes(str)));
        String r=MftStrings.toUtf8String(MftEncrypt.base64Decode(MftStrings.toUtf8Bytes(s)));
        assertEquals(str,r);
    }

    @Test
    void md5() {
        byte[] md5=MftEncrypt.md5(MftStrings.toUtf8Bytes(str), Optional.ofNullable(MftEncrypt.createSalt(10)));
        assertEquals(16,md5.length);
    }

    @Test
    void sha() {
        byte[] md5=MftEncrypt.sha(MftStrings.toUtf8Bytes(str), Optional.ofNullable(MftEncrypt.createSalt(10)));
        assertEquals(20,md5.length);
    }

    @Test
    void sha256() {
        byte[] md5=MftEncrypt.sha256(MftStrings.toUtf8Bytes(str), Optional.ofNullable(MftEncrypt.createSalt(10)));
        assertEquals(32,md5.length);
    }

    @Test
    void sha384() {
        byte[] md5=MftEncrypt.sha384(MftStrings.toUtf8Bytes(str), Optional.ofNullable(MftEncrypt.createSalt(10)));
        assertEquals(48,md5.length);
    }
    @Test
    void sha256WithSalt(){
        byte[] md5=MftEncrypt.sha256(MftStrings.toUtf8Bytes(str), Optional.empty());
        byte[] md25=MftEncrypt.sha256(MftStrings.toUtf8Bytes(str), Optional.ofNullable(MftEncrypt.createSalt(10)));
        assertNotEquals(md5,md25);

        assertEquals(32,md5.length);
    }

    @Test
    void desEncrypt() throws MftException {

        String password="password";
       byte[] bytes = MftEncrypt.desEncrypt(str.getBytes(StandardCharsets.UTF_8)
               ,password.getBytes(StandardCharsets.UTF_8));
       String decoded=MftStrings.toUtf8String(MftEncrypt.desDecrypt(bytes
               ,password.getBytes(StandardCharsets.UTF_8)));
       assertEquals(str,decoded);
    }

    @Test
    void rsaDecrypt() throws MftException, InvalidKeySpecException, NoSuchAlgorithmException {
        String str="abc中国人";
        var pair=MftEncrypt.generatePublicPrivateKeysPair();
        byte[] bytes = MftEncrypt.rsaEncrypt(str.getBytes(StandardCharsets.UTF_8)
        ,pair.getFirst(),true);
        byte[] result=MftEncrypt.rsaDecrypt(bytes,pair.getSecond(),false);
        assertEquals(str,MftStrings.toUtf8String(result));
    }
}