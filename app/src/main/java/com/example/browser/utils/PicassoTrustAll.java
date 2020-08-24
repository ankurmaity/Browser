package com.example.browser.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * @author ankur
 */
public class PicassoTrustAll {

    private static final String TAG = "Picasso";
    private static Picasso mInstance = null;

    public static Picasso getInstance(Context context) {
        if (mInstance == null) {
            new PicassoTrustAll(context);
        }
        return mInstance;
    }

    private PicassoTrustAll(Context context) {
        try {

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            SSLSocketFactory noSSLv3Factory;

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                noSSLv3Factory = new TLSSocketFactory(sslContext.getSocketFactory());
            } else {
                noSSLv3Factory = sslContext.getSocketFactory();
            }

            OkHttpClient.Builder okb = new OkHttpClient.Builder()
                    .sslSocketFactory(noSSLv3Factory, provideX509TrustManager());
            OkHttpClient client = okb.build();

            mInstance = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(client))
                    .listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            Log.e("PICASSO", exception.getMessage());
                        }
                    }).build();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

    public X509TrustManager provideX509TrustManager() {
        try {
            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init((KeyStore) null);
            TrustManager[] trustManagers = factory.getTrustManagers();
            return (X509TrustManager) trustManagers[0];
        } catch (NoSuchAlgorithmException exception) {
            Log.e(getClass().getSimpleName(), "not trust manager available", exception);
        } catch (KeyStoreException exception) {
            Log.e(getClass().getSimpleName(), "not trust manager available", exception);
        }

        return null;
    }

}
