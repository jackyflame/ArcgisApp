package com.haozi.baselibrary.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.haozi.baselibrary.log.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class DeviceConfigUtil {

    public static String sDeviceId;

    public static String appVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void initDevideID(Context context) {
        sDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String deviceOSVersion() {
        return Build.VERSION.RELEASE;
    }

    private static String deviceName() {
        return Build.MODEL;
    }

    public static String getDeviceIDSuffix() {
        if (StringUtil.isEmpty(sDeviceId)) return "";
        int length = sDeviceId.length();
        if (length <= 4) return sDeviceId;
        return sDeviceId.substring(length - 4, length);
    }

    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    public static String getPhoneNumber(Context context) {
        try {
            TelephonyManager phoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = phoneManager.getLine1Number();
            if (StringUtil.isEmpty(phoneNumber) || phoneNumber.length() < 11) return null;
            try {
                phoneNumber = removeCountryCode(phoneNumber);
            } catch (Exception e) {
            }
            return phoneNumber;
        } catch (Exception e) {
            return "";
        }
    }

    private static String removeCountryCode(String number) {
        if (hasCountryCode(number)) {
            int country_digits = number.length() - 11;
            number = number.substring(country_digits);
        }
        return number;
    }

    private static boolean hasCountryCode(String number) {
        return number.charAt(0) == '+';
    }

    public static boolean isActivityForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;
        if (services.get(0) != null &&
                services.get(0).topActivity != null &&
                services.get(0).topActivity.getPackageName() != null &&
                context.getPackageName() != null &&
                services.get(0).topActivity.getPackageName().equalsIgnoreCase(context.getPackageName())) {
            isActivityFound = true;
        }
        return isActivityFound;
    }

    public static boolean isApplicationForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String[] activePackages;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            activePackages = getActivePackages(activityManager);
        } else {
            activePackages = getActivePackagesCompat(activityManager);
        }
        final String current = context.getPackageName();
        if (activePackages != null) {
            for (String activePackage : activePackages) {
                if (activePackage.equals(current)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String[] getActivePackagesCompat(ActivityManager activityManager) {
        final List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    private static String[] getActivePackages(ActivityManager activityManager) {
        final Set<String> activePackages = new HashSet<>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }

    /**
     * 往手机通讯录添加云喇叭联系方式
     * */
    public static void addAsContactAutomatic(ContentResolver contentResolver) {
        String displayName = "云喇叭";
        String mobileNumber = "4001001257";
        String email = "support@shenbianvip.com";

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        // Names
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        displayName).build());

        // Mobile Number
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());

        // Email
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK).build());

        // Asking the Contact provider to create a new contact
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            LogUtil.e("addAsContactAutomatic " + e.getMessage());
            e.printStackTrace();
        }
    }

}
