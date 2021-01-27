
package com.portal.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodsUtil {
    public MethodsUtil() {
    }

    public boolean IsHaveInternet(Context context) {
        try {
            ConnectivityManager e = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo info = e.getActiveNetworkInfo();
            return info != null && info.isConnected();
        } catch (Exception var4) {
            return false;
        }
    }

    public double getDouble(double d) {
        return Double.valueOf((new DecimalFormat("##########.##")).format(d)).doubleValue();
    }

    public boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService("connectivity");
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public boolean check(String phonenumber) {
        String phone = "0\\d{2,3}\\d{7,8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phonenumber);
        return m.matches();
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        String phone = "(1[0-9]{2}){1}\\d{8}";
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*[\\w\\.-]*[a-zA-Z0-9]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkQQ(String qq) {
        Pattern pattern = Pattern.compile("[1-9][0-9]{5,9}");
        Matcher matcher = pattern.matcher(qq);
        return matcher.matches();
    }

    public int roundDIP(Context context, int i) {
        return Math.round(TypedValue.applyDimension(1, (float)i, context.getResources().getDisplayMetrics()));
    }

    public String getMacAddress() {
        try {
            return this.loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
        } catch (IOException var2) {
            return null;
        }
    }

    public String loadFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        boolean numRead = false;

        int numRead1;
        while((numRead1 = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead1);
            fileData.append(readData);
        }

        reader.close();
        return fileData.toString();
    }

    public String getHexString(byte[] b) {
        String result = "";

        for(int i = 0; i < b.length; ++i) {
            result = result + Integer.toString((b[i] & 255) + 256, 16).substring(1);
        }

        return result;
    }

    public byte[] hex2byte(String hex) throws IllegalArgumentException {
        if(hex.length() % 2 != 0) {
            hex = hex + "0";
        }

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

    public String getDivisionString(String sformat, int d) {
        int sl = sformat.length();
        StringBuffer sb = new StringBuffer();
        if(d != 0 && sl != 0) {
            for(int i = 1; i <= (sl % d != 0?sl / d + 1:sl / d); ++i) {
                if(i != (sl % d != 0?sl / d + 1:sl / d)) {
                    sb.append(sformat.substring(i * d - d, d * i));
                    sb.append(" ");
                } else {
                    sb.append(sformat.substring(i * d - d, sl));
                }
            }

            return (new StringBuilder()).append(sb).toString();
        } else {
            return sformat;
        }
    }

    public String getFromAssets(String fileName, Context context) {
        try {
            InputStreamReader exception = new InputStreamReader(context.getResources().getAssets().open(fileName), "GBK");
            BufferedReader bufReader = new BufferedReader(exception);
            String line = "";

            String Result;
            for(Result = ""; (line = bufReader.readLine()) != null; Result = Result + line + "\n") {
                ;
            }

            return Result;
        } catch (Exception var7) {
            return "";
        }
    }

    public boolean isAppInstalledTwo(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List pinfo = packageManager.getInstalledPackages(0);
        ArrayList pName = new ArrayList();
        if(pinfo != null) {
            for(int i = 0; i < pinfo.size(); ++i) {
                String pn = ((PackageInfo)pinfo.get(i)).packageName;
                pName.add(pn);
            }
        }

        return pName.contains(packageName);
    }
}
