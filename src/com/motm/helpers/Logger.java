package com.motm.helpers;

import android.util.Log;

public class Logger
{
    /**Log message
     * @param message 
     */
    public static void d(String message)
    {
        if (message.isEmpty()){
            message = "Empty message";
        }
        
        String tag = getCallingClassName();
        Log.d(tag, message);
    }
    
    /**
     * @return class name
     */
    private static String getCallingClassName()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = stackTraceElements[4].getClassName();    
        return className;
    }
}
