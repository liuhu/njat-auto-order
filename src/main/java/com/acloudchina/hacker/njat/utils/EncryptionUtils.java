package com.acloudchina.hacker.njat.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:03
 **/
public class EncryptionUtils {

    /**
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    private static byte[] hexParseByte2(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * 字符串截取 保留16位
     * @param string
     * @return
     */
    private static String a(String string) {
        String substring;
        if (string.length() > 16) {
            substring = string.substring(0, 16);
        }
        else {
            substring = string;
            if (string.length() < 16) {
                int n = string.length() - 1;
                while (true) {
                    substring = string;
                    if (n >= 15) {
                        break;
                    }
                    string += "\u0000";
                    ++n;
                }
            }
        }
        return substring;
    }

    /**
     * 将字节转成16进制字符串
     * @param array
     * @return
     */
    public static String a(final byte[] array) {
        final StringBuffer sb = new StringBuffer(array.length * 2);
        for (int i = 0; i < array.length; ++i) {
            if ((array[i] & 0xFF) < 16) {
                sb.append("0");
            }
            sb.append(Long.toString(array[i] & 0xFF, 16));
        }
        return sb.toString();
    }

    /**
     * 加密字符串
     * @param str  待加密字符
     * @param key 秘钥
     * @return
     */
    public static String encrypt2Aes(final String str, String key) {
        final String s3 = null;
        final String a = a(key);
        key = s3;
        if (str != null) {
            if (a == null) {
                key = s3;
            }
            else {
                key = a;
                try {
                    if (a.length() != 16) {
                        key = a(a);
                    }
                    final SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
                    final Cipher instance = Cipher.getInstance("AES");
                    instance.init(1, secretKeySpec);
                    key = a(instance.doFinal(str.getBytes("UTF-8"))).toLowerCase();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    key = s3;
                }
            }
        }
        return key;
    }

    /**
     * 将16进制字符串解码
     * @param src
     * @param key
     * @return
     */
    public static String decodeFromAes(String src, String key) {
        try {
            return new String(decodeFromAes(hexParseByte2(src), key));
        } catch (Exception e) {
            return null;
        }
    }

    public static String decodeFromAes2(byte[] content, String key) {
        try {
            return new String(decodeFromAes(content, key));
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] decodeFromAes(byte[] content, String key) throws Exception {
        final SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(content);
    }


    public static void main(String[] args) {
        System.out.println(encrypt2Aes("{\"goodOrderList\":[{\"cardCode\":\"\",\"goodCount\":\"1\",\"goodCutPrice\":\"15\",\"goodFkid\":\"34570075752716613\",\"goodId\":\"203457007574356153000001\",\"goodName\":\"04月16日~11号场-09:00~10:00\",\"goodPrice\":\"15\",\"goodType\":\"1\",\"venueSaleId\":\"903cb7617c284dfc9d2abe6573634c20\"}],\"isCollect\":\"0\",\"orgCode\":\"60fca1582822456bbd5d77f719daca3e\",\"paySource\":\"1\",\"sourceFkId\":\"1124641455777345\",\"sourceName\":\"副馆羽毛球\",\"totalAmount\":\"15.0\",\"totalCutAmount\":\"15.0\",\"userId\":\"34693021860297337\"}", "www.wowsport.cn8"));
    }
}
