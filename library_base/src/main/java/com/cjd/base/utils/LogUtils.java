package com.cjd.base.utils;

import android.util.Log;

import java.util.Locale;

/**
 * 日志工具类
 *
 * @author chenjidong
 */
public class LogUtils {
    private static String className;
    private static String methodName;
    private static int lineNumber;

    private static boolean isDebuggable() {
        return true;
    }

    private static String createLog(String msg) {
        return String.format(Locale.CHINA, "%s(%s:%d)%s", methodName, className, lineNumber, msg);
    }

    private static void getMethodName(Throwable throwable) {
        className = throwable.getStackTrace()[1].getFileName();
        methodName = throwable.getStackTrace()[1].getMethodName();
        lineNumber = throwable.getStackTrace()[1].getLineNumber();
    }

    public static void e(String msg) {
        if (!isDebuggable())
            return;
        getMethodName(new Throwable());
        Log.e(className, createLog(msg));
    }

    public static void w(String msg) {
        if (!isDebuggable())
            return;
        getMethodName(new Throwable());
        Log.w(className, createLog(msg));
    }

    public static void i(String msg) {
        if (!isDebuggable())
            return;
        getMethodName(new Throwable());
        Log.i(className, createLog(msg));
    }

    public static void d(String msg) {
        if (!isDebuggable())
            return;
        getMethodName(new Throwable());
        Log.d(className, createLog(msg));
    }

    public static void v(String msg) {
        if (!isDebuggable())
            return;
        getMethodName(new Throwable());
        Log.v(className, createLog(msg));
    }
}
