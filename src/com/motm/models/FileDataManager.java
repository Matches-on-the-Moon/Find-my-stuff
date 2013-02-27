/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models;

import com.motm.helpers.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class FileDataManager implements DataManager
{
    private ArrayList<Account> accounts;
    private String filename = "accountTable";

    public FileDataManager()
    {
        deserializeTable();
    }
    
    
    private void serializeTable()
    {
    	FileOutputStream fos;
    	ObjectOutputStream oos;
    	
        try {
            fos = new FileOutputStream(filename);
            oos = new ObjectOutputStream( fos );
            oos.writeObject(accounts);
        }
        catch (FileNotFoundException e){
            Logger.e(e.getMessage());
        }
        catch (IOException e) {
            Logger.e(e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void deserializeTable()
    {	
    	FileInputStream fos;
    	ObjectInputStream oos;
    	
    	if(!new File(filename).exists()){
            accounts = new ArrayList<Account>();
            return;
    	}
    	
        try {
            fos = new FileInputStream(filename);
            oos = new ObjectInputStream( fos );
            accounts = (ArrayList<Account>)oos.readObject();
        }  
        catch (FileNotFoundException e) {
            Logger.e(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            Logger.e(e.getMessage());
        } 
        catch (OptionalDataException e) {
            Logger.e(e.getMessage());
        } 
        catch (IOException e) {
            Logger.e(e.getMessage());
        }
        finally {
            // if there was an error, set the accounts to a blank list
            accounts = new ArrayList<Account>();
        }
        
    }
}
