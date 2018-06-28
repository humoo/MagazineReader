package com.hyc.libs.utils;

import android.util.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhxumao on 2017/11/22 12:01.
 */

public class StringUtil {

    protected static MessageDigest messageDigestMD5 = null;
    protected static char[] hexDigits;
    protected static MessageDigest messageDigestSHA256;

    public StringUtil() {
    }

    public static String URLEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception var2) {
            return "";
        }
    }

    public static String URLDecode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception var2) {
            return "";
        }
    }

    /**
     * 如果返回为 null 的值将被置为 "";
     *
     * @param content
     * @return
     */
    public static String getFineText(String content) {
        return isFine(content) ? content : " ";
    }

    public static String stringMD5(byte[] bytes) {
        if (null == messageDigestMD5) {
            return null;
        } else {
            messageDigestMD5.update(bytes);
            return binary2hex(messageDigestMD5.digest());
        }
    }

    public static String stringMD5(String string) {
        return null == messageDigestMD5 ? null : (null != string && 0 != string.length() ? stringMD5(string.getBytes()) : null);
    }

    public static String stringSHA256(byte[] bytes) {
        if (null == messageDigestSHA256) {
            return null;
        } else {
            messageDigestSHA256.update(bytes);
            return binary2hex(messageDigestSHA256.digest());
        }
    }

    public static String stringSHA256(String string) {
        return null == messageDigestSHA256 ? null : (null != string && 0 != string.length() ? stringSHA256(string.getBytes()) : null);
    }

    public static String binary2hex(byte[] bytes) {
        return binary2hex(bytes, 0, bytes.length);
    }

    public static String binary2hex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;

        for (int l = m; l < k; ++l) {
            appendHexPair(bytes[l], stringbuffer);
        }

        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 240) >> 4];
        char c1 = hexDigits[bt & 15];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static String base64Encode(byte[] bytes, int offset, int len) {
        return Base64.encodeToString(bytes, offset, len, 11);
    }

    public static String base64Encode(String string) {
        return null != string && 0 != string.length() ? base64Encode(string.getBytes(), 0, string.length()) : null;
    }

    public static String base64Decode(String base64string) {
        byte[] bytes = Base64.decode(base64string, 11);
        return null == bytes ? null : new String(bytes);
    }

    public static boolean isFine(String str) {
        return str != null && str.trim().length() != 0;
    }

    public static boolean isFine(String str1, String str2) {
        return isFine(str1) && isFine(str2);
    }

    public static boolean isFine(String str1, String str2, String str3) {
        return isFine(str1) && isFine(str2) && isFine(str3);
    }

    public static boolean isFine(String[] str) {
        if (str == null) {
            return false;
        } else {
            for (int i = 0; i < str.length; ++i) {
                if (!isFine(str[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String ArrayToString(String sp, String... strs) {
        if (null != strs && 0 != strs.length) {
            StringBuffer sb = new StringBuffer();
            sb.append(sp);

            for (int i = 0; i < strs.length; ++i) {
                if (strs[i] != null && strs[i].length() >= 1) {
                    sb.append(strs[i]);
                    sb.append(sp);
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String ArrayToStringWrap(String sp, String wrap, boolean cuthf, String... strs) {
        if (null != strs && 0 != strs.length) {
            StringBuffer sb = new StringBuffer();
            sb.append(sp);

            for (int i = 0; i < strs.length; ++i) {
                if (strs[i] != null && strs[i].length() >= 1) {
                    sb.append(wrap);
                    sb.append(strs[i]);
                    sb.append(wrap);
                    sb.append(sp);
                }
            }

            if (cuthf) {
                return cuthf(sp, sb.toString());
            } else {
                return sb.toString();
            }
        } else {
            return "";
        }
    }

    public static String StringWrap(String sp, String warp, boolean cuthf, String warpstrin) {
        return ArrayToStringWrap(sp, warp, cuthf, StringToArray(sp, warpstrin));
    }

    public static String[] StringToArray(String sp, String str) {
        if (null != str && 0 != str.length()) {
            if (str.indexOf(sp) < 0) {
                return new String[]{str};
            } else {
                if (sp.equals(".")) {
                    sp = "\\.";
                }

                if (sp.equals("|")) {
                    sp = "\\|";
                }

                if (sp.equals("\\")) {
                    sp = "\\\\";
                }

                return cuthf(sp, str).split(sp);
            }
        } else {
            return null;
        }
    }

    /**
     * desc:将数组转为16进制
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte aBArray : bArray) {
            sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:将16进制的数据转为数组
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }


    public static String insert(String sp, String des, String src) {
        if (null == des || 0 == des.length()) {
            des = sp;
        }

        return null != src && 0 != src.length() ? (des.indexOf(sp + src + sp) >= 0 ? des : des.concat(src).concat(sp)) : des;
    }

    public static String insert(String sp, String des, String[] srcs) {
        if (null == des || 0 == des.length()) {
            des = "";
        }

        if (null != srcs && 0 != srcs.length) {
            StringBuffer sb = new StringBuffer(des);
            sb.append(sp);

            for (int i = 0; i < srcs.length && null != srcs[i]; ++i) {
                if (sb.indexOf(sp + srcs[i] + sp) <= 0 && 0 != srcs[i].length()) {
                    sb.append(srcs[i]);
                    sb.append(sp);
                }
            }

            return sb.toString().replace(sp + sp, sp);
        } else {
            return des;
        }
    }

    public static String merge(String sp, String des, String src) {
        if (null == des || 0 == des.length()) {
            des = "";
        }

        if (null != src && 0 != src.length()) {
            String[] srcs = StringToArray(sp, src);
            return null != srcs && 0 != srcs.length ? insert(sp, des, srcs).replace(sp + sp, sp) : des;
        } else {
            return des;
        }
    }

    public static String delete(String sp, String des, String src) {
        return null != des && 0 != des.length() ? (null != src && 0 != src.length() ? des.replace(sp + src + sp, sp).replace(sp + sp, sp) : des) : "";
    }

    public static String delete(String sp, String des, String[] srcs) {
        for (int i = 0; i < srcs.length; ++i) {
            des.replace(sp + srcs[i] + sp, sp);
        }

        return des.replace(sp + sp, sp);
    }

    public static int find(String sp, String des, String src) {
        if (null != des && 0 != des.length()) {
            if (null != src && 0 != src.length()) {
                String[] dess = des.split(sp);
                if (null != dess && dess.length >= 0) {
                    int count = 0;

                    for (int i = 0; i < dess.length; ++i) {
                        if (dess[i].equals(src)) {
                            return count;
                        }

                        ++count;
                    }

                    return -3;
                } else {
                    return -3;
                }
            } else {
                return -2;
            }
        } else {
            return -1;
        }
    }

    public static int findlast(String sp, String des, String src) {
        if (null != des && 0 != des.length()) {
            if (null != src && 0 != src.length()) {
                String[] dess = des.split(sp);
                if (null != dess && dess.length >= 0) {
                    int count = 0;

                    for (int i = dess.length - 1; i >= 0; --i) {
                        if (dess[i].equals(src)) {
                            return count;
                        }

                        ++count;
                    }

                    return -3;
                } else {
                    return -3;
                }
            } else {
                return -2;
            }
        } else {
            return -1;
        }
    }

    public static int find(String sp, String des, String[] srcs) {
        int count = 0;
        if (null != des && 0 != des.length()) {
            if (null != srcs && 0 != srcs.length) {
                for (int i = 0; i < srcs.length && null != srcs[i]; ++i) {
                    if (des.indexOf(sp + srcs[i]) >= 0) {
                        ++count;
                    }
                }

                return count == srcs.length ? 0 : -3;
            } else {
                return -2;
            }
        } else {
            return -1;
        }
    }

    public static int count(String sp, String str) {
        return null != str && str.length() >= 9 ? cuthf(sp, str).split(sp).length : 0;
    }

    public static String cuthf(String sp, String str) {
        if (null != str && str.length() >= 3) {
            StringBuffer sb = new StringBuffer(str);
            if (sb.charAt(sb.length() - 1) == sp.charAt(0)) {
                sb.deleteCharAt(sb.length() - 1);
            }

            if (sb.charAt(0) == sp.charAt(0)) {
                sb.deleteCharAt(0);
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static final String replace(String s, String s1, String s2) {
        if (s == null) {
            return null;
        } else {
            byte i = 0;
            int i1;
            if ((i1 = s.indexOf(s1, i)) < 0) {
                return s;
            } else {
                char[] ac = s.toCharArray();
                char[] ac1 = s2.toCharArray();
                int j = s1.length();
                StringBuffer sb = new StringBuffer(ac.length);
                sb.append(ac, 0, i1).append(ac1);
                i1 += j;

                int k;
                for (k = i1; (i1 = s.indexOf(s1, i1)) > 0; k = i1) {
                    sb.append(ac, k, i1 - k).append(ac1);
                    i1 += j;
                }

                sb.append(ac, k, ac.length - k);
                return sb.toString();
            }
        }
    }

    public static String replaceAll(String str, String pattern, String to) {
        Pattern p = Pattern.compile(pattern, 2);
        StringBuffer sb = new StringBuffer();
        Matcher m = null;
        m = p.matcher(str);

        while (m.find()) {
            m.appendReplacement(sb, to);
        }

        m.appendTail(sb);
        return sb.toString();
    }

    public static String replaceAll2(String str, String pattern, String to) {
        Pattern p = Pattern.compile(pattern, 32);
        StringBuffer sb = new StringBuffer();
        Matcher m = null;
        m = p.matcher(str);

        while (m.find()) {
            m.appendReplacement(sb, to);
        }

        m.appendTail(sb);
        return sb.toString();
    }

    static {
        try {
            messageDigestMD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
        }

        hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        messageDigestSHA256 = null;

        try {
            messageDigestSHA256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException var1) {
            var1.printStackTrace();
        }

    }

}
