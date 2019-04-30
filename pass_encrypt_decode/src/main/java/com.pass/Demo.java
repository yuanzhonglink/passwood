package com.pass;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


/**
 * @author yuanzhonglin
 * @date 2019/4/29
 * @Description:
 */
public class Demo {

    public static void main(String[] args) {
        try {
            String encrypt = encrypt("12");
            System.out.println(encrypt);
            String decode = decode(encrypt);
            System.out.println(decode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加密
    public static String encrypt(String str) throws Exception{
        Key key = getKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodeResult = cipher.doFinal(str.getBytes());
        return Hex.encodeHexString(encodeResult);
    }

    //解密
    public static String decode(String str) throws Exception{
        Key key = getKey();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeResult = cipher.doFinal(str.getBytes());
        return new String(decodeResult);
    }

    public static Key getKey() throws Exception{
        //生成Key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        //keyGenerator.init(128, new SecureRandom("seedseedseed".getBytes()));
        //使用上面这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();

        //Key转换
        Key key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }
}
