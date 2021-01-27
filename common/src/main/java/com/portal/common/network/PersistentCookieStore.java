
package com.portal.common.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class PersistentCookieStore implements CookieStore {
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String COOKIE_NAME_STORE = "names";
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private boolean omitNonPersistentCookies = false;
    private final ConcurrentHashMap cookies = new ConcurrentHashMap();
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore(Context context) {
        this.cookiePrefs = context.getSharedPreferences(context.getPackageName() + "." + "CookiePrefsFile", 0);
        String storedCookieNames = this.cookiePrefs.getString("names", (String)null);
        if(storedCookieNames != null) {
            String[] cookieNames = TextUtils.split(storedCookieNames, ",");
            String[] as = cookieNames;
            int j = cookieNames.length;

            for(int i = 0; i < j; ++i) {
                String name = as[i];
                String encodedCookie = this.cookiePrefs.getString("cookie_" + name, (String)null);
                if(encodedCookie != null) {
                    Cookie decodedCookie = this.decodeCookie(encodedCookie);
                    if(decodedCookie != null) {
                        this.cookies.put(name, decodedCookie);
                    }
                }
            }

            this.clearExpired(new Date());
        }

    }

    public void addCookie(Cookie cookie) {
        if(!this.omitNonPersistentCookies || cookie.isPersistent()) {
            String name = cookie.getName() + cookie.getDomain();
            if(!cookie.isExpired(new Date())) {
                this.cookies.put(name, cookie);
            } else {
                this.cookies.remove(name);
            }

            Editor prefsWriter = this.cookiePrefs.edit();
            prefsWriter.putString("names", TextUtils.join(",", this.cookies.keySet()));
            prefsWriter.putString("cookie_" + name, this.encodeCookie(new SerializableCookie(cookie)));
            prefsWriter.commit();
        }
    }

    public void clear() {
        Editor prefsWriter = this.cookiePrefs.edit();
        Iterator iterator = this.cookies.keySet().iterator();

        while(iterator.hasNext()) {
            String name = (String)iterator.next();
            prefsWriter.remove("cookie_" + name);
        }

        prefsWriter.remove("names");
        prefsWriter.commit();
        this.cookies.clear();
    }

    public boolean clearExpired(Date date) {
        boolean clearedAny = false;
        Editor prefsWriter = this.cookiePrefs.edit();
        Iterator iterator = this.cookies.entrySet().iterator();

        while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            String name = (String)entry.getKey();
            Cookie cookie = (Cookie)entry.getValue();
            if(cookie.isExpired(date)) {
                this.cookies.remove(name);
                prefsWriter.remove("cookie_" + name);
                clearedAny = true;
            }
        }

        if(clearedAny) {
            prefsWriter.putString("names", TextUtils.join(",", this.cookies.keySet()));
        }

        prefsWriter.commit();
        return clearedAny;
    }

    public List getCookies() {
        return new ArrayList(this.cookies.values());
    }

    public void setOmitNonPersistentCookies(boolean omitNonPersistentCookies) {
        this.omitNonPersistentCookies = omitNonPersistentCookies;
    }

    public void deleteCookie(Cookie cookie) {
        String name = cookie.getName();
        this.cookies.remove(name);
        Editor prefsWriter = this.cookiePrefs.edit();
        prefsWriter.remove("cookie_" + name);
        prefsWriter.commit();
    }

    protected String encodeCookie(SerializableCookie cookie) {
        if(cookie == null) {
            return null;
        } else {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            try {
                ObjectOutputStream e = new ObjectOutputStream(os);
                e.writeObject(cookie);
            } catch (Exception var4) {
                return null;
            }

            return this.byteArrayToHexString(os.toByteArray());
        }
    }

    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = this.hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie)objectInputStream.readObject()).getCookie();
        } catch (Exception var6) {
            ;
        }

        return cookie;
    }

    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        byte[] abyte0 = bytes;
        int j = bytes.length;

        for(int i = 0; i < j; ++i) {
            byte element = abyte0[i];
            int v = element & 255;
            if(v < 16) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(v));
        }

        return sb.toString().toUpperCase(Locale.US);
    }

    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];

        for(int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }

        return data;
    }
}
