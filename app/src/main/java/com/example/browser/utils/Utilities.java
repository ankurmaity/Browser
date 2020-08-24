package com.example.browser.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.browser.ApplicationClass;
import com.example.browser.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Utilities {

    public static boolean has(String field) {
        return field != null && !field.equals("") && !field.equalsIgnoreCase("null");
    }

    public static boolean has(double field) {
        return field != 0;
    }

    public static boolean has(boolean field) {
        return field;
    }

    public static boolean has(BigDecimal field) {
        if (field == null)
            return false;
        else
            return field.compareTo(BigDecimal.ZERO) != 0;
    }

    public static boolean has(Object field) {
        return field != null;
    }

    public static boolean has(Map field) {
        return field != null && field.size() != 0;
    }

    public static boolean has(List field) {
        return field != null && field.size() > 0;
    }

    public static boolean has(JSONObject json, String field) {

        if (json == null)
            return false;

        try {

            if (json.isNull(field))
                return false;

            if (json.has(field) && has(json.getString(field)))
                return true;
            else return false;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean has(ArrayList list) {
        return list != null && !list.isEmpty();
    }

    public static boolean has(CopyOnWriteArrayList list) {
        return list != null && !list.isEmpty();
    }

    public static boolean has(Set set) {
        return set != null && !set.isEmpty();
    }

    public static boolean has(View view, int field) {

        if (view == null)
            return false;

        return view.findViewById(field) != null;
    }

    public static boolean has(String[] array) {
        return array != null && array.length != 0;
    }

    public static boolean has(JSONArray array) {
        return array != null && array.length() != 0;
    }

    public static boolean equals(String a, String b) {
        if (!has(a) && !has(b))
            return true;
        else if (has(a) && !has(b))
            return false;
        else if (!has(a) && has(b))
            return false;
        else return a.equals(b);
    }

    /**
     * To check internet connection
     *
     * @param context
     * @return
     * @throws Exception
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(Context context) throws Exception {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isFailover())
            return false;
        else if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    /**
     * Fetch icon from website
     * @param context
     * @param imageView
     * @param url
     */
    public static void fetchIconFromWebsite(Context context, ImageView imageView, String url) {
        String baseUrl = "";
        try {
            URL tempURL = new URL(url);
            baseUrl = tempURL.getProtocol() + "://" + tempURL.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_new_tab));
            return;
        }
        baseUrl += "/favicon.ico";
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                PicassoTrustAll.getInstance(context).load(baseUrl).fit().centerCrop().into(imageView);
            } else {
                Picasso.get().load(baseUrl).fit().centerCrop().into(imageView);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate random string
     * @param n
     * @return
     */
    public static String getRandomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        com.example.browser.utils.Log.d("TAG",sb.toString());
        return sb.toString();
    }
}
