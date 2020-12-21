package com.huttchang.global.provider;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

/**
 * 양방향 암호화 알고리즘인 AES256 암호화를 지원하는 클래스
 */
public class AESUtils {
    private final static String iv =  "xhterjSeencetliv";
    private final static String key = "xhterjSeenciretl";

    /**
     * AES256으로 암호화된 txt를 복호화
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static String decrypt(String str) throws NoSuchAlgorithmException,
        GeneralSecurityException, UnsupportedEncodingException {
        byte[] encrypted1 = new Base64().decode(str);

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString.trim();
    }
}