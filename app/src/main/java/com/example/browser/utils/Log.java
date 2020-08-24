package com.example.browser.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.browser.BuildConfig;
import com.example.browser.utils.Utilities;

/**
 * @author Koustuv
 */

public class Log {

    static final boolean LOG = BuildConfig.DEBUG;
    static final int chunkSize = 2048;
    public static Handler mHandler = new Handler(Looper.getMainLooper());
    private static String TAG = "CustomLog";

    public static void i(String tag, String string) {
        if (string == null) return;
        if (LOG) {
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.i(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void e(String tag, Exception e) {
        if (e == null) e = new Exception();
        String string = getStackTrace(e);
        if (LOG) {
            if (string == null) {
                android.util.Log.e(tag, "");
                return;
            }
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.e(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
//            CrashlyticsHelper.logException(e);
        }
    }

    public static void e(String tag, OutOfMemoryError e) {
        String string = getStackTrace(e);
        if (LOG) {
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.e(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
//            CrashlyticsHelper.logException(e);
        }
    }

    public static void e(String tag, String string) {
        if (string == null) return;
        if (LOG) {
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.e(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }

            Exception exception = new Exception(string);
            exception.setStackTrace(cleanTrace(exception));
//            CrashlyticsHelper.logException(exception);
        }
    }

    public static void d(String tag, String string) {
        if (string == null) return;
        if (LOG) {
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.d(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void v(String tag, String string) {
        if (string == null) return;
        if (LOG) {
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.v(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void w(String tag, String string) {
        if (string == null) return;
        if (LOG) {
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.w(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void wtf(String tag, String string) {
        if (string == null) return;
        if (LOG) {
            for (int i = 0; i < string.length(); i += chunkSize) {
                android.util.Log.wtf(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void i(String tag, String string, Throwable tr) {
        if (string == null) return;
        if (LOG) {
            android.util.Log.i(tag, string.substring(0, Math.min(string.length(), chunkSize)), tr);
            for (int i = chunkSize; i < string.length(); i += chunkSize) {
                android.util.Log.i(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void e(String tag, String string, Throwable tr) {
        if (string == null) return;
        if (LOG) {
            android.util.Log.e(tag, string.substring(0, Math.min(string.length(), chunkSize)), tr);
            for (int i = chunkSize; i < string.length(); i += chunkSize) {
                android.util.Log.e(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }

            Exception exception = new Exception(string, tr);
//            CrashlyticsHelper.logException(exception);
        }
    }

    public static void d(String tag, String string, Throwable tr) {
        if (string == null) return;
        if (LOG) {
            android.util.Log.d(tag, string.substring(0, Math.min(string.length(), chunkSize)), tr);
            for (int i = chunkSize; i < string.length(); i += chunkSize) {
                android.util.Log.d(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void v(String tag, String string, Throwable tr) {
        if (string == null) return;
        if (LOG) {
            android.util.Log.v(tag, string.substring(0, Math.min(string.length(), chunkSize)), tr);
            for (int i = chunkSize; i < string.length(); i += chunkSize) {
                android.util.Log.v(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void w(String tag, String string, Throwable tr) {
        if (string == null) return;
        if (LOG) {
            android.util.Log.w(tag, string.substring(0, Math.min(string.length(), chunkSize)), tr);
            for (int i = chunkSize; i < string.length(); i += chunkSize) {
                android.util.Log.w(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void wtf(String tag, String string, Throwable tr) {
        if (string == null) return;
        if (LOG) {
            android.util.Log.wtf(tag, string.substring(0, Math.min(string.length(), chunkSize)), tr);
            for (int i = chunkSize; i < string.length(); i += chunkSize) {
                android.util.Log.wtf(tag, string.substring(i, Math.min(string.length(), i + chunkSize)));
            }
        }
    }

    public static void toast(Context context, String tag, String string) {
        makeText(context, string, Toast.LENGTH_SHORT);
        Log.v(tag, string);
    }

    public static void toastLong(Context context, String tag, String string) {
        makeText(context, tag + "_" + string, Toast.LENGTH_LONG);
        Log.v(tag, string);
    }

    private static void makeText(final Context context, final String toast, final int length) {
        mHandler.post(new Runnable() {
            public void run() {
                Log.v(TAG, toast);
                Toast.makeText(context, toast, length).show();
            }
        });
    }


    private static StackTraceElement[] cleanTrace(Throwable e) {
        StackTraceElement[] cleanedUpStackTrace = new StackTraceElement[e.getStackTrace().length - 1];
        System.arraycopy(e.getStackTrace(), 1, cleanedUpStackTrace, 0, cleanedUpStackTrace.length);
        return cleanedUpStackTrace;
    }

    /**
     * This method is used to get the stack trace of the exception
     *
     * @param throwable
     * @return
     */
    private static String getStackTrace(Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (Utilities.has(throwable)) {
                StackTraceElement[] stackTraceElement = throwable.getStackTrace();
                if (!Utilities.has(stackTraceElement)) {
                    return stringBuilder.toString();
                }
                stringBuilder.append(throwable.getMessage());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    stringBuilder.append(System.lineSeparator());
                } else {
                    stringBuilder.append("\n");
                }
                for (StackTraceElement traceElement : stackTraceElement) {
                    stringBuilder.append(traceElement.toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        stringBuilder.append(System.lineSeparator());
                    } else {
                        stringBuilder.append("\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
