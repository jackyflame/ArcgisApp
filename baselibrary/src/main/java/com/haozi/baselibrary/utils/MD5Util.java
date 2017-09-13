package com.haozi.baselibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Android Studio.
 * User:  jf.yin
 * Date: 2016/8/17
 * Time: 14:11
 */
public class MD5Util {

    public final static String MD5bup(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5 32位加密
     * @param plainText 需要加密字段
     * */
    public static String Md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5 32位加密[大写]
     * @param plainText 需要加密字段
     * */
    public static String Md5UpperCase(String plainText) {
        String MD5Str = Md5(plainText);
        return MD5Str.toUpperCase();
    }

    /**
     * MD5 32位加密 链接TOKEN
     * @param phoneNum 电话号码
     * */
    public static String Md5Token(String phoneNum, String clientId){
        if(StringUtil.isMobileNum(phoneNum) == false){
            return null;
        }
        return Md5UpperCase(phoneNum + clientId);
    }

    /**
     * MD5 32位加密 链接TOKEN
     * @param phoneNum 电话号码
     * */
    public static String Md5TokenForVip(String clientId, String phoneNum){
        if(StringUtil.isMobileNum(phoneNum) == false || StringUtil.isEmpty(clientId)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String salt = "56fd3af693514dee88d15f56cc1e477a";
        return Md5(clientId+phoneNum+dateStr+salt);
    }

    /**
     * MD5 32位加密 链接TOKEN
     * @param phoneNum 电话号码
     * */
    public static String Md5TokenForAdver(String clientId, String phoneNum, String deviceId){
        if(StringUtil.isMobileNum(phoneNum) == false || StringUtil.isEmpty(clientId)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String salt = "56fd3af693514dee88d15f56cc1e477a";
        return Md5(clientId+phoneNum+deviceId+dateStr+salt);
    }

    public static String CreatePhoneCallUUID(long time, long userId, String floadCode, int index){
        //获取数字标记
        String indexString = floadCode;
        //去掉字母
        if(!StringUtil.isEmpty(indexString)){
            indexString = indexString.replaceAll("[a-zA-Z]","");
        }
        //默认数值（列表长度+1）
        if (StringUtil.isEmpty(indexString)) {
            indexString = String.valueOf(index);
        }
        //返回UUID
        return CreatePhoneCallUUID(time,userId,indexString);
    }

    public static String CreatePhoneCallUUID(long userId, String indexString){
        return CreatePhoneCallUUID(System.currentTimeMillis(),userId,indexString);
    }

    public static String CreatePhoneCallUUID(long time, long userId, String indexString){
        //4位随机数
        int numcode = (int) ((Math.random() * 9 + 1) * 1000);
        //返回UUID
        return String.valueOf(time + DeviceConfigUtil.getDeviceIDSuffix() + userId + indexString);
    }
}
