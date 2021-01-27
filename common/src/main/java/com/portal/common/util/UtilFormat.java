
package com.portal.common.util;

public class UtilFormat {
    public UtilFormat() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static int formatInt(String str) {
        try {
            return str != null && !str.equals("")?Integer.parseInt(str):-1;
        } catch (Exception var2) {
            return -2;
        }
    }
}
