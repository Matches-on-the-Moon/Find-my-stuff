package com.motm.adapters;

public class RowAccount
{
    private int imageId;
    private String loginName;
    private String email;
 
    public RowAccount(int imageId, String loginName, String email) 
    {
        this.imageId = imageId;
        this.loginName = loginName;
        this.email = email;
    }
    public int getImageId() 
    {
        return imageId;
    }
    public void setImageId(int imageId) 
    {
        this.imageId = imageId;
    }
    public String getLoginName() 
    {
        return loginName;
    }
    public void setLoginName(String loginName) 
    {
        this.loginName = loginName;
    }
    public String getEmail() 
    {
        return email;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }
    @Override
    public String toString() 
    {
        return loginName + "\n" + email;
    }
}
