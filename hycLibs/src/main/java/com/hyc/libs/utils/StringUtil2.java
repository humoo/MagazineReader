package com.hyc.libs.utils;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by zhxumao on 2017/11/22 12:04.
 */

public class StringUtil2 {
    private static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '-'};
    static String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public StringUtil2() {
    }

    public static int abs(int v) {
        return Math.abs(v);
    }

    public static long abs(long v) {
        return Math.abs(v);
    }

    public static Integer var(int v) {
        try {
            return Integer.valueOf(v);
        } catch (Exception var2) {
            return Integer.valueOf(0);
        }
    }

    public static Long var(long v) {
        try {
            return Long.valueOf(v);
        } catch (Exception var3) {
            return Long.valueOf(0L);
        }
    }

    public static Float var(float v) {
        try {
            return Float.valueOf(v);
        } catch (Exception var2) {
            return Float.valueOf(0.0F);
        }
    }

    public static Character var(char v) {
        try {
            return Character.valueOf(v);
        } catch (Exception var2) {
            return Character.valueOf('\u0000');
        }
    }

//    public static Byte var(byte v) {
//        try {
//            return Byte.valueOf(v);
//        } catch (Exception var2) {
//            var2.printStackTrace();
//            return Byte.valueOf(-128);
//        }
//    }

    public static Date date(String v) {
        try {
            return new Date(s2l(v));
        } catch (Exception var2) {
            var2.printStackTrace();
            return new Date(System.currentTimeMillis());
        }
    }

    public static Date date(long v) {
        try {
            return new Date(v);
        } catch (Exception var3) {
            var3.printStackTrace();
            return new Date(System.currentTimeMillis());
        }
    }

    public static int intvar(String v) {
        try {
            return Integer.valueOf(v).intValue();
        } catch (Exception var2) {
            return 0;
        }
    }

    public static long longvar(String v) {
        try {
            return Long.valueOf(v).longValue();
        } catch (Exception var2) {
            return 0L;
        }
    }

    public static String c10to64(long num) {
        return toUnsignedString(num, 6);
    }

    public static String c10to32(long num) {
        return toUnsignedString(num, 5);
    }

    public static String c10to16(long num) {
        return Long.toHexString(num).toUpperCase();
    }

    public static String c10to8(long num) {
        return Long.toOctalString(num);
    }

    public static String c10to4(long num) {
        return toUnsignedString(num, 2);
    }

    public static String c10to2(long num) {
        return Long.toBinaryString(num);
    }

    public static long c64to10(String num) {
        return toUnsignedLong(num, 6);
    }

    public static long c32to10(String num) {
        return toUnsignedLong(num, 5);
    }

    public static long c16to10(String num) {
        return toUnsignedLong(num, 4);
    }

    public static long c8to10(String num) {
        return toUnsignedLong(num, 3);
    }

    public static long c4to10(String num) {
        return toUnsignedLong(num, 2);
    }

    public static long c2to10(String num) {
        return toUnsignedLong(num, 1);
    }

    private static String toUnsignedString(long i, int shift) {
        int bit = (int)Math.pow(2.0D, (double)shift);
        char[] buf = new char[bit];
        int charPos = bit;
        int radix = 1 << shift;
        long mask = (long)(radix - 1);

        do {
            --charPos;
            buf[charPos] = digits[(int)(i & mask)];
            i >>>= shift;
        } while(i != 0L);

        return new String(buf, charPos, bit - charPos);
    }

    private static long toUnsignedLong(String src, int shift) {
        int bit = (int)Math.pow(2.0D, (double)shift);
        long result = 0L;
        int len = src.length();
        int num = len;
        boolean b = false;
        String single = null;

        for(int i = 0; i < len; ++i) {
            single = src.substring(i, i + 1);
            single = String.valueOf(getNum(single));
            int var10 = Integer.parseInt(single);
            result += (long)var10 * (long)Math.pow((double)bit, (double)(num - 1));
            --num;
        }

        return result;
    }

    public static int getNum(String single) {
        int result = 0;

        for(int n = 0; n < 64; ++n) {
            String str = String.valueOf(digits[n]);
            if(single.equals(str)) {
                result = n;
                break;
            }
        }

        return result;
    }

    public static long s2l(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception var2) {
            return -1L;
        }
    }

    public static int s2i(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception var2) {
            return -1;
        }
    }

    public static int s2i(String str, int defaultInt) {
        try {
            return Integer.parseInt(str);
        } catch (Exception var3) {
            return defaultInt;
        }
    }

    public static long s2l(String str, long defaultLong) {
        try {
            return Long.parseLong(str);
        } catch (Exception var4) {
            return defaultLong;
        }
    }

    public static double s2d(String str, double defaultFloat) {
        try {
            return Double.parseDouble(str);
        } catch (Exception var4) {
            return defaultFloat;
        }
    }

    public static int force_s2i(String str) {
        byte[] bs = str.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean isstart = false;

        for(int i = 0; i < bs.length; ++i) {
            if(bs[i] >= 48 && bs[i] <= 57) {
                isstart = true;
                baos.write(bs[i]);
            } else if(isstart) {
                break;
            }
        }

        return s2i(baos.toString());
    }

    public static long force_s2l(String str) {
        byte[] bs = str.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean isstart = false;

        for(int i = 0; i < bs.length; ++i) {
            if(bs[i] >= 48 && bs[i] <= 57) {
                isstart = true;
                baos.write(bs[i]);
            } else if(isstart) {
                break;
            }
        }

        return s2l(baos.toString());
    }

    public static String hex2bin(String hex) {
        String bstr = "";
        String tmpstr = "";
        if(null != hex && hex.length() != 0) {
            char[] bitchars = hex.toCharArray();

            for(int i = 2; i < bitchars.length; ++i) {
                tmpstr = Long.toBinaryString(Long.decode("0x" + bitchars[i]).longValue());
                if(tmpstr.length() == 1) {
                    tmpstr = "000" + tmpstr;
                }

                if(tmpstr.length() == 2) {
                    tmpstr = "00" + tmpstr;
                }

                if(tmpstr.length() == 3) {
                    tmpstr = "0" + tmpstr;
                }

                bstr = bstr + tmpstr;
            }
        } else {
            bstr = Long.toBinaryString(0L);
        }

        return bstr;
    }

    public static String bin2hex(String bstr) {
        String hstr = "";
        int j = 0;

        for(int i = 4; i <= bstr.length(); i += 4) {
            String tmpstr1 = bstr.substring(j, i);
            j = i;
            String tmpstr2 = Long.toHexString(Long.parseLong(tmpstr1, 2));
            hstr = hstr + tmpstr2;
        }

        return "0x" + hstr;
    }

    public static String c10to62(long num) {
        if(num < 1L) {
            return "0";
        } else {
            StringBuilder sb;
            for(sb = new StringBuilder(); num > 0L; num /= 62L) {
                sb.append(ALPHABET.charAt((int)(num % 62L)));
            }

            return sb.toString();
        }
    }

    public static long c62to10(String str) {
        str = str.trim();
        if(str.length() < 1) {
            return 0L;
        } else {
            long result = 0L;

            for(int i = 0; i < str.length(); ++i) {
                result = (long)((double)result + (double)ALPHABET.indexOf(str.charAt(i)) * Math.pow(62.0D, (double)i));
            }

            return result;
        }
    }
}
