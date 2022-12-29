package com.restaurant.dinehouse.util;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.logging.Logger;

public class CipherUtil {

    public static final Logger logger = Logger.getLogger(CipherUtil.class.getName());

    public static String[] decodeBase64Str(String str) {
        return new String(Base64.getMimeDecoder().decode(str.getBytes())).split(":");
    }

    public static String getMessageDigest(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes("UTF-8"));
            byte byteData[] = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    public static String getSHA256Digest(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            byte byteData[] = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

}
