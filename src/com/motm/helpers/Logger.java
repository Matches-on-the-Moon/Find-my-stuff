/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.helpers;

import android.util.Log;

/**
 *
 * @author michael
 */
public class Logger
{
    // debug message
    public static void d(String message)
    {
        String tag = getCallingClassName();
                
        Log.d(tag, message);
    }
    
    // error message
    public static void e(String message)
    {
        String tag = getCallingClassName();
                
        Log.e(tag, message);
    }
    
    private static String getCallingClassName()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
       
        String className = stackTraceElements[4].getClassName();
        // only use lass part: com.stuff.className
        //className = fullClassName.substring(fullClassName.lastIndexOf('.')+1);
        
        return className;
    }
}
