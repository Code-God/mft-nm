package com.meifute.core.tool;

import com.meifute.core.collection.Pair;
import com.meifute.core.lang.MftException;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

/**
 * @author wzeng
 * @date 2020/8/7
 * @Description
 */
public class MftEncrypt {

    static Des des = new Des();
    static RSA rsa = new RSA();

    public static byte[] desEncrypt(byte[] bytes, byte[] password) throws MftException {
        try {
            return des.encrypt(bytes,password,true);
        } catch (Exception e) {
           throw new MftException(e.getMessage(),e);
        }
    }

    public static  byte[] desDecrypt(byte[] bytes,byte[] password) throws MftException {
        try {
            return des.decrypt(bytes,password,true);
        } catch (Exception e) {
            throw new MftException(e.getMessage(),e);
        }
    }


    public static byte[] rsaEncrypt(byte[] bytes, byte[] password,boolean isPublicKey) throws MftException {
        try {
            return rsa.encrypt(bytes,password,isPublicKey);
        } catch (Exception e) {
            throw new MftException(e.getMessage(),e);
        }
    }

    public static  byte[] rsaDecrypt(byte[] bytes,byte[] password,boolean isPublicKey) throws MftException {
        try {
            return rsa.decrypt(bytes,password,isPublicKey);
        } catch (Exception e) {
            throw new MftException(e.getMessage(),e);
        }
    }

    public static Pair<byte[],byte[]> generatePublicPrivateKeysPair() throws MftException {
        try {
            return RSA.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
           throw new MftException(e.getMessage(),e);
        }
    }



    public static byte[] base64Encode(byte[] base64){
        return Base64.getEncoder().encode(base64);
    }

    public static byte[] base64Decode(byte[] coded){
        return Base64.getDecoder().decode(coded);
    }

    public static byte[] md5(byte[] bytes, Optional<byte[]> salt) {
        MessageDigest md =getMessageDigest(MessageDigestType.MD5);
        return digestWithSalt(bytes, salt, md);
    }

    private static byte[] digestWithSalt(byte[] bytes, Optional<byte[]> salt, MessageDigest md) {
        salt.ifPresent(md::update);
        md.update(bytes);
        return md.digest();
    }

    public static byte[] sha(byte[] bytes, Optional<byte[]> salt){
        MessageDigest md =getMessageDigest(MessageDigestType.SHA);
        return digestWithSalt(bytes, salt, md);
    }

    public static byte[] sha256(byte[] bytes, Optional<byte[]> salt){
        MessageDigest md =getMessageDigest(MessageDigestType.SHA256);
        return digestWithSalt(bytes, salt, md);
    }

    public static byte[] sha384(byte[] bytes, Optional<byte[]> salt){
        MessageDigest md= getMessageDigest(MessageDigestType.SHA384);
        return digestWithSalt(bytes, salt, md);
    }

    public static byte[] createSalt(int byteWidth){
        SecureRandom secureRandom =new SecureRandom();
        byte[] bytes = new byte[byteWidth];
        secureRandom.nextBytes(bytes);
        return bytes;
    }




    private static MessageDigest getMessageDigest(MessageDigestType type){
        try {
            return MessageDigest.getInstance(type.getValue());
        } catch (NoSuchAlgorithmException e) {
            //Do nothing, do not think this exception will happen.
            e.printStackTrace();
        }
        return null;
    }

    public enum CryptAlgorithm{
        DES,RSA;
    }

    public  enum  MessageDigestType {
        MD5("MD5"),
        SHA("SHA"),
        SHA256("SHA-256"),
        SHA384("SHA-384");

        private String value = null;

         MessageDigestType(String value){
            this.value = value;
        }

        public String getValue(){
             return this.value;
        }
    }
}

abstract class  EncryptBase{

    public abstract  Key generatePublicKey(byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException;
    public abstract Key generatePrivateKey(byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException;
    public abstract String getInstanceCipherName();
    public AlgorithmParameterSpec getAlgorithmParametersSpec(){
        return null;
    }
    public Cipher createCliperKey(byte[] password,int mode,boolean isPublicKey) throws NoSuchPaddingException, NoSuchAlgorithmException
            , InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        Key key=null;
        if(isPublicKey) {
            key = generatePublicKey(password);
        }else{
            key = generatePrivateKey(password);
        }
        Cipher cipher = Cipher.getInstance(getInstanceCipherName());
        AlgorithmParameterSpec spec = getAlgorithmParametersSpec();
        if(spec ==null){
            cipher.init(mode,key);
        } else{
            cipher.init(mode,key,spec);
        }
        return cipher;
    }

     public byte[] encrypt(byte[] bytes,byte[] publicKey,boolean isPublicKey) throws InvalidKeyException, NoSuchAlgorithmException
             , NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = createCliperKey(publicKey,Cipher.ENCRYPT_MODE,isPublicKey);
        return cipher.doFinal(bytes);
    }

    public byte[] decrypt(byte[] bytes,byte[] publicKey,boolean isPublicKey) throws InvalidKeyException, NoSuchAlgorithmException
            , NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = createCliperKey(publicKey,Cipher.DECRYPT_MODE,isPublicKey);
        return cipher.doFinal(bytes);
    }


}

class Des extends  EncryptBase{
    static final byte[] IV_DSE_PARAMETER=new byte[]{2,0,2,0,0,8,0,7};
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    private   Key generateKey(byte[] desKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec desKeySpec = new DESKeySpec(desKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MftEncrypt.CryptAlgorithm.DES.name());
        return keyFactory.generateSecret(desKeySpec);
    }

    @Override
    public AlgorithmParameterSpec getAlgorithmParametersSpec() {
        return new IvParameterSpec(IV_DSE_PARAMETER);
    }

    @Override
    public Key generatePublicKey(byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return generateKey(publicKey);
    }

    @Override
    public Key generatePrivateKey(byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return generateKey(privateKey);
    }

    @Override
    public String getInstanceCipherName() {
        return CIPHER_ALGORITHM;
    }
}
class RSA extends  EncryptBase{
    static final byte[] IV_DSE_PARAMETER=new byte[]{2,0,2,0,0,8,0,7};
    public static Pair<byte[], byte[]> generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(MftEncrypt.CryptAlgorithm.RSA.name());
        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        return Pair.of(publicKey, privateKey);

    }

    public RSAPublicKey generatePublicKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        var x509KeySpec = new X509EncodedKeySpec(key);
        RSAPublicKey pubKey= (RSAPublicKey) KeyFactory.getInstance(MftEncrypt.CryptAlgorithm.RSA.name())
                .generatePublic(x509KeySpec);
        return pubKey;
    }

    public RSAPrivateKey generatePrivateKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPrivateKey privateKey= (RSAPrivateKey) KeyFactory.getInstance(MftEncrypt.CryptAlgorithm.RSA.name())
                .generatePrivate(new PKCS8EncodedKeySpec(key));
        return privateKey;
    }


    @Override
    public String getInstanceCipherName() {
        return MftEncrypt.CryptAlgorithm.RSA.name();
    }


}
