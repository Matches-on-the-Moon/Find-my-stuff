package com.motm.helpers;

import android.util.Log;

public class Logger
{
    public static void d(String message)
    {
        String tag = getCallingClassName();
        Log.d(tag, message);
    }
    
    private static String getCallingClassName()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = stackTraceElements[4].getClassName();    
        return className;
    }
}
