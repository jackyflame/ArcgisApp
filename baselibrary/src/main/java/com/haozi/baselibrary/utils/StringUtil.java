package com.haozi.baselibrary.utils;

import android.text.Html;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtil {

    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null || TextUtils.isEmpty(charSequence)) return true;
        return TextUtils.isEmpty(charSequence.toString().trim());
    }

    public static boolean isMobileNum(CharSequence phoneNumber) {
        if (isEmpty(phoneNumber)) return false;
        Pattern p = Pattern.compile("^((1[3-9][0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static boolean isLetter(CharSequence phoneNumber) {
        if (isEmpty(phoneNumber)) return false;
        Pattern p = Pattern.compile("^[a-zA-Z]*$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public static String formatDouble(double value) {
        return String.format("%.2f", value);
    }

    public static String formatDouble(String value) {
        return String.format("%.2f", value);
    }

    public static String getFormatMoney(String money){
        return String.format("%s元",money);
    }

    public static boolean isInteger(String input) {
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);
        return mer.find();
    }

    public static boolean isMoneyNumber(String input) {
        Matcher mer = Pattern.compile("^(([1-9]\\\\d*)|(0))(.\\\\d{0,2})?$").matcher(input);
        return mer.find();
    }

    public static boolean isFloat(String input) {
        Matcher mer = Pattern.compile("^(-?\\d+)(\\.\\d+)?$").matcher(input);
        return mer.find();
    }

    public static boolean isChinese(String input) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static String getCode(String smsContent) {
        if (isEmpty(smsContent)) return null;
        Pattern pattern = Pattern.compile("[\\d]{4}");
        Matcher matcher = pattern.matcher(smsContent);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        for (String code : result) {
            if (!StringUtil.isEmpty(code)) return code;
        }
        return null;
    }

    public static String getFormatPhoneNumber(String phoneNumber) {
        if (isEmpty(phoneNumber) || !isMobileNum(phoneNumber)) return null;
        String areaCode = phoneNumber.substring(0, 3);
        String prefix = phoneNumber.substring(3, 7);
        String rest = phoneNumber.substring(7);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(areaCode);
        stringBuilder.append("-");
        stringBuilder.append(prefix);
        stringBuilder.append("-");
        stringBuilder.append(rest);
        return stringBuilder.toString();
    }

    public static String getHtmlStr(String content){
        if(StringUtil.isEmpty(content)){
            return "";
        }
        return Html.fromHtml(content).toString();
    }
}
