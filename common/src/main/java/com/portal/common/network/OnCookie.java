//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.network;

import android.content.Context;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

public interface OnCookie {
    void getCookie(Context var1, HttpResponse var2, DefaultHttpClient var3);

    void setCookie(Context var1, HttpRequestBase var2, DefaultHttpClient var3);
}
