package com.frame.common.utils;

import java.security.MessageDigest;

/**
 * 字符串长度压缩
 */
public class ShortStringUtil {
    
    public static String shortText(String string) {
        String key = "FRAME";            //自定义生成MD5加密字符串的混合KEY
        String[] chars = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        String hex = MD5Encode(key + string);
        int hexLen = hex.length();
        int subHexLen = hexLen / 8;
        StringBuilder shortStr = new StringBuilder();
        
        for (int i = 0; i < subHexLen; i++) {
            String outChars = "";
            int j = i + 1;
            String subHex = hex.substring(i * 8, j * 8);
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);
            
            for (int k = 0; k < 8; k++) {
                int index = (int) (Long.valueOf("0000003D", 16) & idx);//这里取5位没有要求吗？不是取连续的5位
                outChars += chars[index];
                idx = idx >> 5;
            }
            shortStr.append(outChars);
        }
        return shortStr.toString();
    }
    
    private final static String[] hexDigits = {
        "0", "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "a", "b", "c", "d", "e", "f"
    };
    
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            resultString.trim();
            
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception ex) {
        }
        return resultString;
    }


	/*public static void main(String[] args){
		String content = "akey1=abcddd&objc[69485]: Class JavaLaunchHelper is implemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/bin/java (0x10f0e84c0) and /Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/libinstrument.dylib (0x1101674e0). One of the two will be used. Which one is undefined.\n=sdfdsfdsfdsflkfjsdlkfldjfdsf&dfdsfklsdfkldsfkldsf=fdslfkdlsfk";
		System.out.println(shortText(content));
	}*/
}