package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoAu;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: a.class */
public class a {
    private String a;
    private String b;

    private static byte[] d(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i << 1;
            int i3 = i2 + 1;
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i2, i3), 16) << 4) + Integer.parseInt(str.substring(i3, i2 + 2), 16));
        }
        return bArr;
    }

    private static byte[] c(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            return str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public a(String str) {
        this.a = e(str);
        this.b = e(this.a);
    }

    private String e(String str) {
        if (str == null) {
            return null;
        }
        try {
            return c(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static String c(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                int i = b & 255;
                if ((i >> 4) == 0) {
                    sb.append("0");
                    sb.append(Integer.toHexString(i));
                } else {
                    sb.append(Integer.toHexString(i));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static String b(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            return new String(bArr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        Locale locale = Locale.ENGLISH;
        if (bArr == null) {
            return "";
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            sb.append(hexString.toUpperCase(locale));
        }
        return sb.toString();
    }

    private byte[] a(int i, String str, byte[] bArr) {
        SecretKeySpec secretKeySpec;
        if (str == null) {
            return null;
        }
        try {
            secretKeySpec = new SecretKeySpec(str.getBytes("utf-8"), "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            secretKeySpec = null;
        }
        if (secretKeySpec == null) {
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(d(this.b));
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(i, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            return null;
        }
    }

    public String a(String str) {
        byte[] a;
        byte[] c = c(str);
        if (c == null || (a = a(1, this.a, c)) == null) {
            return null;
        }
        String a2 = a(a);
        if (a2.equals("")) {
            return null;
        }
        return a2;
    }

    public String b(String str) {
        byte[] a;
        byte[] d = d(str);
        if (d == null || (a = a(2, this.a, d)) == null) {
            return null;
        }
        return b(a);
    }
}
