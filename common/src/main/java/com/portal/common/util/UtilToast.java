
package com.portal.common.util;

import android.content.Context;
import android.widget.Toast;

public class UtilToast {
    public UtilToast() {
    }

    public static void show(Context context, String str) {
        if(!str.equals(""))
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int id) {
        Toast.makeText(context, context.getString(id), Toast.LENGTH_SHORT).show();
    }
}
