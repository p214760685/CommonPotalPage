
package com.portal.common.network;

import android.os.Build.VERSION;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

public class TrustAllSSLSocketFactory extends SSLSocketFactory {
    private javax.net.ssl.SSLSocketFactory factory;
    private static TrustAllSSLSocketFactory instance;

    private TrustAllSSLSocketFactory() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        super((KeyStore)null);
        SSLContext context = SSLContext.getInstance("TLS");
        context.init((KeyManager[])null, new TrustManager[]{new TrustAllSSLSocketFactory.TrustAllManager()}, (SecureRandom)null);
        this.factory = context.getSocketFactory();
        this.setHostnameVerifier(new X509HostnameVerifier() {
            public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
            }

            public void verify(String host, X509Certificate cert) throws SSLException {
            }

            public void verify(String host, SSLSocket ssl) throws IOException {
            }

            public boolean verify(String host, SSLSession session) {
                return true;
            }
        });
    }

    public static SocketFactory getDefault() {
        if(instance == null) {
            try {
                instance = new TrustAllSSLSocketFactory();
            } catch (KeyManagementException var1) {
                var1.printStackTrace();
            } catch (UnrecoverableKeyException var2) {
                var2.printStackTrace();
            } catch (NoSuchAlgorithmException var3) {
                var3.printStackTrace();
            } catch (KeyStoreException var4) {
                var4.printStackTrace();
            }
        }

        return instance;
    }

    public Socket createSocket() throws IOException {
        return this.factory.createSocket();
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        if(VERSION.SDK_INT < 11) {
            this.injectHostname(socket, host);
        }

        return this.factory.createSocket(socket, host, port, autoClose);
    }

    private void injectHostname(Socket socket, String host) {
        try {
            Field field = InetAddress.class.getDeclaredField("hostName");
            field.setAccessible(true);
            field.set(socket.getInetAddress(), host);
        } catch (Exception var4) {
            ;
        }

    }

    public class TrustAllManager implements X509TrustManager {
        public TrustAllManager() {
        }

        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
