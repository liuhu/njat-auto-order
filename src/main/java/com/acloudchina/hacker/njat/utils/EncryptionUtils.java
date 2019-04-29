package com.acloudchina.hacker.njat.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:03
 **/
@Slf4j
public class EncryptionUtils {


    /**
     * 加密字符串
     * @param str  待加密字符
     * @param key 秘钥
     * @return
     */
    public static String encrypt2Aes(final String str, String key) {
        final String s3 = null;
        final String a = keySubStr(key);
        key = s3;
        if (str != null) {
            if (a == null) {
                key = s3;
            }
            else {
                key = a;
                try {
                    if (a.length() != 16) {
                        key = keySubStr(a);
                    }
                    final SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
                    final Cipher instance = Cipher.getInstance("AES");
                    instance.init(1, secretKeySpec);
                    key = byte2Hex(instance.doFinal(str.getBytes("UTF-8"))).toLowerCase();
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
            final SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("ASCII"), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(hexParseByte2(src)));
        } catch (Exception e) {
            log.error("解码失败, key = {}, src = {}", key, src);
            return null;
        }
    }

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
     * @param key
     * @return
     */
    private static String keySubStr(String key) {
        String substring;
        if (key.length() > 16) {
            substring = key.substring(0, 16);
        }
        else {
            substring = key;
            if (key.length() < 16) {
                int n = key.length() - 1;
                while (true) {
                    substring = key;
                    if (n >= 15) {
                        break;
                    }
                    key += "\u0000";
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
    private static String byte2Hex(final byte[] array) {
        final StringBuilder sb = new StringBuilder(array.length * 2);
        for (int i = 0; i < array.length; ++i) {
            if ((array[i] & 0xFF) < 16) {
                sb.append("0");
            }
            sb.append(Long.toString(array[i] & 0xFF, 16));
        }
        return sb.toString();
    }
}
