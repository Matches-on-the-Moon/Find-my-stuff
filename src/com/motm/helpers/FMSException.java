/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.helpers;

/**
 * A generic exception so I can tell the difference between my exceptions and all others
 */
public class FMSException extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message error message
     */
    public FMSException(String message)
    {
        super(message);
    }
}
