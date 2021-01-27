//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.network;

import android.content.Context;
import android.os.Message;

import com.portal.common.util.MethodsUtil;
import com.portal.common.util.UtilLog;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;


public class NetworkingService {
    private boolean isDebug = true;
    public static final int POST = 596377;
    public static final int GET = 596376;
    public static final int FORM = 596375;
    private Context context;
    private OnCookie onCookie;
    private Map<String,String>  headMap ;

    public NetworkingService(Context context) {
        this.context = context;
        this.onCookie = null;
    }

    public NetworkingService(Context context, Map<String,String>  headMap) {
        this.context = context;
        this.headMap = headMap ;
    }

    public NetworkingService(Context context, OnCookie onCookie) {
        this.context = context;
        this.onCookie = onCookie;
    }

    public Message httpPostAndGet(String url, int httpTeyp, OnHttp http) {
        Message mes = new Message();
        if(this.context != null && !(new MethodsUtil()).IsHaveInternet(this.context)) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException var6) {
            }

            mes.what = AsyncTask.WIFI_NOT_OPEN;
            mes.arg1 = AsyncTask.WIFI_NOT_OPEN;
            mes.obj = "网络未连接,请连接网络";
            UtilLog.shownNet("data=" + (String)mes.obj, this.isDebug);
            return mes;
        } else if(http == null) {
            mes.what = AsyncTask.LOCAL_ANOMALIES;
            mes.arg1 = AsyncTask.LOCAL_ANOMALIES;
            mes.obj = "Error message:OnHttp cannot be null";
            UtilLog.shownNet("data=" + (String)mes.obj, this.isDebug);
            return mes;
        } else {
            String e1;
            if(httpTeyp == POST) {
                e1 = (String)http.getData();
                this.httpPost(url, e1, mes);
                if(mes.what == AsyncTask.OPERATION_WIN) {
                    http.analyticData(mes);
                }

                UtilLog.shownNet("data=" + (String)mes.obj, this.isDebug);
                return mes;
            } else if(httpTeyp == GET) {
                e1 = (String)http.getData();
                this.httpGet(url + (e1 != null && !e1.equals("")?"?" + e1:""), mes);
                if(mes.what == AsyncTask.OPERATION_WIN) {
                    http.analyticData(mes);
                }

                UtilLog.shownNet("data=" + (String)mes.obj, this.isDebug);
                return mes;
            } else if(httpTeyp != FORM) {
                mes.what = AsyncTask.LOCAL_ANOMALIES;
                mes.arg1 = AsyncTask.LOCAL_ANOMALIES;
                mes.obj = "Error message:only support NetworkingService.FORM/POST/GET";
                UtilLog.shownNet("data=" + (String)mes.obj, this.isDebug);
                return mes;
            } else {
                try {
                    Object[] e = (Object[])((Object[])http.getData());
                    this.httpForm(url, (Map)e[0], (Map)e[1], mes, e.length > 2 && e[2] != null?(Map)e[2]:null, e.length > 3 && e[3] != null?(String)e[3]:"utf-8");
                    if(mes.what == AsyncTask.OPERATION_WIN) {
                        http.analyticData(mes);
                    }
                } catch (Exception var7) {
                    mes.what = AsyncTask.LOCAL_ANOMALIES;
                    mes.arg1 = AsyncTask.LOCAL_ANOMALIES;
                    mes.obj = "网络连接错误\n请确认网络是否正确连接";
                }

                UtilLog.shownNet("data=" + (String)mes.obj, this.isDebug);
                return mes;
            }
        }
    }

    private void httpPost(String url, String data, Message mes) {
        UtilLog.shownNet("POST url=" + url + "\tPOST=" + data, this.isDebug);
        if(mes == null) {
            mes = new Message();
        }

        try {
            HttpPost e = new HttpPost(url);
            if(headMap != null && headMap.size() > 0){//设置头文件
                for (Entry<String, String> arg : headMap.entrySet()) {
                    e.setHeader(arg.getKey(),arg.getValue());
                }
            }
            StringEntity se = new StringEntity(data, "UTF-8");
            e.setEntity(se);
            e.addHeader("Content-Type", "application/json; charset=utf-8");
            DefaultHttpClient httpClient = this.supportHttps();
            if(this.onCookie != null) {
                this.onCookie.setCookie(this.context, e, httpClient);
            }

            HttpResponse httpResponse = httpClient.execute(e);
            int code = httpResponse.getStatusLine().getStatusCode();
            if(200 <= code && code <= 207) {
                if(this.onCookie != null) {
                    this.onCookie.getCookie(this.context, httpResponse, httpClient);
                }

                mes.what = AsyncTask.OPERATION_WIN;
                mes.arg1 = code;
                if(code == 200) {
                    mes.obj = EntityUtils.toString(httpResponse.getEntity());
                } else {
                    mes.obj = "";
                }
            }else if(code == 401){
                mes.what = AsyncTask.OPERATION_GRANT;
                mes.arg1 = code;
                mes.obj =  EntityUtils.toString(httpResponse.getEntity());
            } else {
                mes.what = AsyncTask.SERVICE_EXCEPTION;
                mes.arg1 = code;
                mes.obj = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IllegalArgumentException var9) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        } catch (IOException var10) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        } catch (Exception var11) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        }

    }

    private void httpGet(String url, Message mes) {
        UtilLog.shownNet("GET url=" + url, this.isDebug);
        if(mes == null) {
            mes = new Message();
        }

        try {

            HttpGet e = new HttpGet(url);
            if(headMap != null && headMap.size() > 0){//设置头文件
                for (Entry<String, String> arg : headMap.entrySet()) {
                    e.setHeader(arg.getKey(),arg.getValue());
                }
            }
            DefaultHttpClient httpClient = this.supportHttps();
            if(this.onCookie != null) {
                this.onCookie.setCookie(this.context, e, httpClient);
            }
            HttpResponse httpResponse = httpClient.execute(e);
            int code = httpResponse.getStatusLine().getStatusCode();
            if(200 <= code && code <= 207) {
                if(this.onCookie != null) {
                    this.onCookie.getCookie(this.context, httpResponse, httpClient);
                }

                mes.what = AsyncTask.OPERATION_WIN;
                mes.arg1 = code;
                if(code == 200) {
                    mes.obj = EntityUtils.toString(httpResponse.getEntity());
                } else {
                    mes.obj = "";
                }
            }else if(code == 401){
                mes.what = AsyncTask.OPERATION_GRANT;
                mes.arg1 = code;
                mes.obj =   EntityUtils.toString(httpResponse.getEntity());
            } else {
                mes.what = AsyncTask.SERVICE_EXCEPTION;
                mes.arg1 = code;
                mes.obj = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IllegalArgumentException var7) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误";
        } catch (IOException var8) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        } catch (Exception var9) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        }

    }

    private void httpForm(String url, Map mMap, Map fileMap, Message mes, Map mimeType, String charset) {
        UtilLog.shownNet("url=" + url + "\t\tfromdata=" + (mMap != null?mMap.toString():""), this.isDebug);

        try {
            HttpPost e = new HttpPost(url);
            if(headMap != null && headMap.size() > 0){//设置头文件
                for (Entry<String, String> arg : headMap.entrySet()) {
                    e.setHeader(arg.getKey(),arg.getValue());
                }
            }
            DefaultHttpClient httpClient = this.supportHttps();
            MultipartEntity entity = new MultipartEntity();
            if(mMap != null) {
                Iterator code = mMap.entrySet().iterator();

                while(code.hasNext()) {
                    Entry response = (Entry)code.next();
                    entity.addPart((String)response.getKey(), new StringBody("" + response.getValue()));
                }
            }

            if(fileMap != null) {
                Iterator response1 = fileMap.entrySet().iterator();

                while(response1.hasNext()) {
                    Entry code1 = (Entry)response1.next();
                    if(code1.getValue() != null) {
                        entity.addPart((String)code1.getKey(), new FileBody((File)code1.getValue(), mimeType != null?(String)mimeType.get(code1.getKey()):"image/jpeg", charset));
                    }
                }
            }

            e.setEntity(entity);
            if(this.onCookie != null) {
                this.onCookie.setCookie(this.context, e, httpClient);
            }

            HttpResponse response2 = httpClient.execute(e);
            int code2 = response2.getStatusLine().getStatusCode();
            if(200 <= code2 && code2 <= 207) {
                if(this.onCookie != null) {
                    this.onCookie.getCookie(this.context, response2, httpClient);
                }

                mes.what = AsyncTask.OPERATION_WIN;
                mes.arg1 = code2;
                if(code2 == 200) {
                    mes.obj = EntityUtils.toString(response2.getEntity());
                } else {
                    mes.obj = "";
                }
            }else if(code2 == 401){
                mes.what = AsyncTask.OPERATION_GRANT;
                mes.arg1 = code2;
                mes.obj =  EntityUtils.toString(response2.getEntity());
            } else {
                mes.what = AsyncTask.SERVICE_EXCEPTION;
                mes.arg1 = code2;
                mes.obj = EntityUtils.toString(response2.getEntity());
            }
        } catch (ClientProtocolException var12) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        } catch (IOException var13) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        } catch (Exception var14) {
            mes.what = AsyncTask.FAILURE_IN_LINK;
            mes.arg1 = AsyncTask.FAILURE_IN_LINK;
            mes.obj = "网络连接错误\n请确认网络是否正确连接";
        }

    }

    private DefaultHttpClient supportHttps() {
        short timeOut = 30000;
        BasicHttpParams param = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(param, timeOut);
        HttpConnectionParams.setSoTimeout(param, timeOut);
        HttpConnectionParams.setTcpNoDelay(param, true);
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", TrustAllSSLSocketFactory.getDefault(), 443));
        ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(param, registry);
        DefaultHttpClient client = new DefaultHttpClient(manager, param);
        return client;
    }
}
