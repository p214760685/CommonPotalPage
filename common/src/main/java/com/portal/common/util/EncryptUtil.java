
package com.portal.common.util;

import java.security.MessageDigest;
import java.util.Random;

public class EncryptUtil {
    public EncryptUtil() {
    }

    public byte[] generateKey() {
        Random random = new Random();
        byte[] bytes = new byte[8];

        for(int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte)random.nextInt(255);
        }

        return bytes;
    }

    public String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        if(b == null) {
            return hs;
        } else {
            for(int i = 0; i < b.length; ++i) {
                stmp = Integer.toHexString(b[i] & 255);
                if(stmp.length() == 1) {
                    hs = hs + "0" + stmp;
                } else {
                    hs = hs + stmp;
                }
            }

            return hs;
        }
    }

    public byte[] hex2byte(String hex) throws IllegalArgumentException {
        if(hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            char[] arr = hex.toCharArray();
            byte[] b = new byte[hex.length() / 2];
            int i = 0;
            int j = 0;

            for(int l = hex.length(); i < l; ++j) {
                String swap = "" + arr[i++] + arr[i];
                int byteint = Integer.parseInt(swap, 16) & 255;
                b[j] = (new Integer(byteint)).byteValue();
                ++i;
            }

            return b;
        }
    }

    public String getMD5(String content) {
        String s = null;
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(content.getBytes());
            byte[] tmp = e.digest();
            char[] str = new char[32];
            int k = 0;

            for(int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return s;
    }
}
