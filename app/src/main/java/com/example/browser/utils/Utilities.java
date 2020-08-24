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

/*

    public static ProgressDialog progressDialog(Context context, String message) {

        ProgressDialog dialog = new ProgressDialog(context, R.style.MyTheme);

//        mProgressDialog.setMessage(message);
//        mProgressDialog.setIndeterminate(false);


        setDefaultParams(dialog);
        dialog.setCancelable(true);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);

        return dialog;
    }*/

//    public static ProgressDialog showProgressDialog(Context activity) {
//
//        ProgressDialog dialog = new ProgressDialog(activity);
//        if (!dialog.isShowing()) {
//            dialog.show();
//        }
//
//        return dialog;
//    }

//    public static ProgressDialog hideProgressDialog(ProgressDialog dialog) {
//
//        if (Utilities.has(dialog) && dialog.isShowing()) {
//            dialog.dismiss();
//        }
//
//        return dialog;
//    }

    public static String getFormattedDate(Date date, String pattern) {
        String formattedDate = null;
        if (Utilities.has(date)) {
            // Default pattern
            if (!Utilities.has(pattern))
                pattern = "yyyy-MM-dd";

            SimpleDateFormat format = new SimpleDateFormat(pattern);
            formattedDate = format.format(date);
        }

        return formattedDate;
    }

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
     * Check the wifi enabled or not and scan the nearest wifi names.
     *
     * @throws Exception
     */
    public static boolean checkNetworkAvailability(final Context activity) throws Exception {

        // Check network availability
        boolean networkAvailable = false;
        networkAvailable = isNetworkAvailable(activity);

        // If the network is available, return
        if (networkAvailable)
            return true;

        // Get WiFi manager
        final WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null)
            return false;

        return false;
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return 0;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    public static String format(double value) {

        if (Double.isNaN(value) | Double.isInfinite(value)) {
            return "-";
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(round(value, 2));
    }

    public static String format(BigDecimal value) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(value);
    }

    public static String formatCompact(double value) {
        DecimalFormat df = new DecimalFormat();
        if (value < 1000) {
            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            return df.format(round(value, 2));
        } else {
            df.setMaximumFractionDigits(0);
            df.setMinimumIntegerDigits(0);
            return df.format(round(value, 0));
        }
    }

    public static String formatDecimals(double price, int decimals) {

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(decimals);
        df.setMinimumFractionDigits(decimals);

        return df.format(price);
    }

    public static String formatDecimals(BigDecimal price, int decimals) {

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(decimals);
        df.setMinimumFractionDigits(decimals);

        return df.format(price);
    }

    public static String formatDigits(long value, int digits) {
        DecimalFormat df = new DecimalFormat();
        df.setMinimumIntegerDigits(digits);
        return df.format(value);
    }

    // For Android 6.0 to check permission
    @TargetApi(23)
    public static boolean checkPermissions(Context context, String permission) {
        boolean verifyPermission = true;
        //        Log.e(TAG, "Current permission :: " + context.checkSelfPermission(permission));
        //        Log.e(TAG, "Permission granred :: " + PackageManager.PERMISSION_GRANTED);

        //        Log.e(TAG,context.check)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            //            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},1409);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            verifyPermission = false;
        }
        return verifyPermission;
    }

    /**
     * Returns a copy of the object, or null if the object cannot be serialized.
     *
     * @param orig
     * @return
     */
    public static Object copy(Object orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();
            // Make an input stream from the byte array and read a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }

    /**
     * Check is string contains only numeric charaters
     *
     * @param text
     * @return
     */
    public static boolean isNumeric(String text) {
        return TextUtils.isDigitsOnly(text);
    }

    /**
     * Get android sdk version
     *
     * @return sdk version of android
     */
    public static int getAndroidSdkVersion() {
        return Build.VERSION.SDK_INT;
    }


    public static void show(View view, int resource) {
        if (view.findViewById(resource) != null)
            view.findViewById(resource).setVisibility(View.VISIBLE);
    }

    public static void hide(View view, int resource) {
        if (view.findViewById(resource) != null)
            view.findViewById(resource).setVisibility(View.GONE);
    }

    public static void invisible(View view, int resource) {
        if (view.findViewById(resource) != null)
            view.findViewById(resource).setVisibility(View.INVISIBLE);
    }


    public static void listen(View view, int resource, View.OnClickListener listener) {
        if (view != null && view.findViewById(resource) != null)
            view.findViewById(resource).setOnClickListener((View.OnClickListener) listener);

    }

    public static void setText(View view, int resource, final String text) {
        if (view != null && view.findViewById(resource) != null)
            ((TextView) view.findViewById(resource)).setText(text);
    }

  /*  public static void loadImageInside(Context context, String url, ImageView view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
            PicassoTrustAll.getInstance(context).load(url).fit().centerInside().into(view);
        else
            Picasso.get().load(url).fit().centerInside().into(view);
    }

    public static void loadImageInside(Context context, int drawable, ImageView view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
            PicassoTrustAll.getInstance(context).load(drawable).fit().centerInside().into(view);
        else
            Picasso.get().load(drawable).fit().centerInside().into(view);
    }*/

    public static void loadImageInside(Context context, Bitmap bitmap, ImageView view) {
       /* try {
            Uri uri = Uri.fromFile(File.createTempFile("temp_file_name", ".jpg", context.getCacheDir()));
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
                PicassoTrustAll.getInstance(context).load(uri).fit().centerInside().into(view);
            else
                Picasso.get().load(uri).fit().centerInside().into(view);
        } catch (Exception e) {
            Log.e("LoadBitmapByPicasso", e.getMessage());
        }*/
    }

    public static void makeToast(String message) {
        Toast.makeText(ApplicationClass.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static String serverDateFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd");
    }

    public static String serverDateFormat(Calendar c) {
        return serverDateFormat(c.getTime());
    }

    public static String dateFormat(Calendar c, String format) {
        return dateFormat(c.getTime(), format);
    }

    public static String dateFormat(Date c, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                DateFormat df = new SimpleDateFormat("dd-MM-yyy");
                return df.format(c.getTime());
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public static boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static String getPath(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

    public static String getFileName(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void openFile(Activity activity, File file) {
//
//        try {
//
//            Uri uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//
//            if (file.toString().toLowerCase().contains(".doc") || file.toString().toLowerCase().contains(".docx")) {
//                // Word document
//                intent.setDataAndType(uri, "application/msword");
//            } else if (file.toString().toLowerCase().toLowerCase().contains(".pdf")) {
//                // PDF file
//                intent.setDataAndType(uri, "application/pdf");
//            } else if (file.toString().toLowerCase().contains(".ppt") || file.toString().toLowerCase().contains(".pptx")) {
//                // Powerpoint file
//                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
//            } else if (file.toString().toLowerCase().contains(".xls") || file.toString().toLowerCase().contains(".xlsx")) {
//                // Excel file
//                intent.setDataAndType(uri, "application/vnd.ms-excel");
//            } else if (file.toString().toLowerCase().contains(".zip")) {
//                // ZIP file
//                intent.setDataAndType(uri, "application/zip");
//            } else if (file.toString().toLowerCase().contains(".rar")) {
//                // RAR file
//                intent.setDataAndType(uri, "application/x-rar-compressed");
//            } else if (file.toString().toLowerCase().contains(".rtf")) {
//                // RTF file
//                intent.setDataAndType(uri, "application/rtf");
//            } else if (file.toString().toLowerCase().contains(".wav") || file.toString().toLowerCase().contains(".mp3")) {
//                // WAV audio file
//                intent.setDataAndType(uri, "audio/x-wav");
//            } else if (file.toString().toLowerCase().contains(".gif")) {
//                // GIF file
//                intent.setDataAndType(uri, "image/gif");
//            } else if (file.toString().toLowerCase().contains(".jpg") || file.toString().toLowerCase().contains(".jpeg") || file.toString().toLowerCase().contains(".png")) {
//                // JPG file
//                intent.setDataAndType(uri, "image/jpeg");
//            } else if (file.toString().toLowerCase().contains(".txt")) {
//                // Text file
//                intent.setDataAndType(uri, "text/plain");
//            } else if (file.toString().toLowerCase().contains(".3gp") || file.toString().toLowerCase().contains(".mpg") ||
//                    file.toString().toLowerCase().contains(".mpeg") || file.toString().toLowerCase().contains(".mpe") || file.toString().toLowerCase().contains(".mp4") || file.toString().toLowerCase().contains(".avi")) {
//                // Video files
//                intent.setDataAndType(uri, "video/*");
//            } else {
//                intent.setDataAndType(uri, "*/*");
//            }
//
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            activity.startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(activity, "No application found which can open the file", Toast.LENGTH_SHORT).show();
//        }
    }

    public static File getFile(String dir, String filename) {
        String path;

        if (Utilities.has(dir)) {
            path = Environment.getExternalStorageDirectory().toString() + "/" + dir + "/" + filename;
        } else {
            path = Environment.getExternalStorageDirectory().toString() + "/" + filename;
        }

        File file = new File(path);

        return file;
    }

    /*public static boolean isFileExist(Context context, Uri uri) {
        File file = null;
        try {
            file = FileUtil.from(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return file.exists();
    }*/

    public static boolean isFileExist(String dir, String filename) {
        String path;

        if (Utilities.has(dir)) {
            path = Environment.getExternalStorageDirectory().toString() + "/" + dir + "/" + filename;
        } else {
            path = Environment.getExternalStorageDirectory().toString() + "/" + filename;
        }

        File file = new File(path);

        return file.exists();
    }

    public static void setIcon(Activity activity, ImageView view, String file) {
      /*  if (file.toLowerCase().contains(".doc") || file.toLowerCase().contains(".docx")) {
            Utilities.loadImageInside(activity, R.drawable.ic_doc, view);
        } else if (file.toLowerCase().contains(".pdf")) {
            Utilities.loadImageInside(activity, R.drawable.ic_pdf, view);
        } else if (file.toLowerCase().contains(".ppt") || file.contains(".pptx")) {
            Utilities.loadImageInside(activity, R.drawable.ic_ppt, view);
        } else if (file.toLowerCase().contains(".xls") || file.contains(".xlsx")) {
            Utilities.loadImageInside(activity, R.drawable.ic_xls, view);
        } else if (file.toLowerCase().contains(".zip") || file.toLowerCase().contains(".rar")) {
            Utilities.loadImageInside(activity, R.drawable.ic_zip, view);
        } else if (file.toLowerCase().contains(".wav") || file.contains(".mp3")) {
            Utilities.loadImageInside(activity, R.drawable.ic_mp3, view);
        } else if (file.toLowerCase().contains(".jpg") || file.toLowerCase().contains(".jpeg") || file.toLowerCase().contains(".png")) {
            Utilities.loadImageInside(activity, file, view);
        } else if (file.toLowerCase().contains(".txt")) {
            Utilities.loadImageInside(activity, R.drawable.ic_txt, view);
        } else if (file.toLowerCase().contains(".html")) {
            Utilities.loadImageInside(activity, R.drawable.ic_html, view);
        } else {
            Utilities.loadImageInside(activity, R.drawable.ic_unknown_file, view);
        }*/
    }

    public static Bitmap getResizedBitmap(Bitmap bm) {
        float width = bm.getWidth();
        float height = bm.getHeight();

        float newWidth = width > height ? 1024 : 768;
        float newHeight = width > height ? 768 : 1024;

        if (width < newHeight && height < newHeight) {
            newWidth = width;
            newHeight = height;
        } else if (width < height) {
            newHeight = height * (newWidth / width);
        } else {
            newWidth = width * (newHeight / height);
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, (int) width, (int) height, matrix, false);
//        bm.recycle();
        return resizedBitmap;
    }

    public static void downloadFiles(final Object fileObject, final Activity activity) {
    /*    if (!Utilities.has(fileObject)) {
            return;
        }

        EventModel.AttachmentModel attModel = null;
        HomeworkModel.AttachmentModel hwAttModel = null;
        String[] file;
        String fileLink;

        if (fileObject instanceof EventModel.AttachmentModel) {
            attModel = (EventModel.AttachmentModel) fileObject;
            file = attModel.getFile_link().split("/");
            fileLink = attModel.getFile_link();
        } else {
            hwAttModel = (HomeworkModel.AttachmentModel) fileObject;
            file = hwAttModel.getFile_link().split("/");
            fileLink = hwAttModel.getFile_link();
        }

        final String fileName = file[file.length - 1];
        new DownloadTask(activity, fileLink, fileName, new IResposeCallBack() {
            @Override
            public void onSuccess(String tag, String data) {
                Utilities.openFile(activity, Utilities.getFile(IConstant.FOLDER, fileName));
            }

            @Override
            public void onError(String tag, String data) {
                Utilities.makeToast(data);
            }
        });*/
    }

    public static void writeCacheFile(Context context, String filename, String content) {
        String TAG = "CACHE_WRITE_FILE";

        File file = new File(context.getCacheDir(), filename);

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static String readCacheFile(Context context, String filename) {
        String content = "";
        String TAG = "CACHE_READ_FILE";
        String strLine = "";
        StringBuilder text = new StringBuilder();
        File cDir = context.getCacheDir();
        File tempFile = new File(cDir.getPath() + "/" + filename);

        try {
            FileReader fReader = new FileReader(tempFile);
            BufferedReader bReader = new BufferedReader(fReader);

            while ((strLine = bReader.readLine()) != null) {
                text.append(strLine + "\n");
            }
            content = text.toString();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return content;
    }

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
