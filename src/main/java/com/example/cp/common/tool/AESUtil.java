package com.example.cp.common.tool;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-23
 */
@Slf4j
public class AESUtil {
    private static final String CIPHER_INSTANCE = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    public static void main(String args[]) throws Exception {
//        String key = "358d71c554ae78914fece40609aad77b";
        String key = "12345678123456781234567812345678";//32位
        String iv = "2222222222222222";//16位
//        String iv = "F3a22EcceB2e0t13";
        String data = "{\"Uid\":123456,\"LoginType\":1,\"LoginTime\":\"2020-05-26T15:15:30.1048153+08:00\"}";
        String s=encrypt(data,key,iv);
        System.out.println("加密后："+s);
        System.out.println("解密后："+desEncrypt(s,key,iv));


    }

    public static String encrypt(String data,String key,String iv) throws Exception {
        try {

            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
//            if (plaintextLength % blockSize != 0) {
//                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
//            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desEncrypt(String data,String key,String iv) throws Exception {
        try
        {

            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
