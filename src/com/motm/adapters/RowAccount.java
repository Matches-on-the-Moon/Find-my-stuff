package com.motm.adapters;

public class RowAccount
{
    private int imageId;
    private String loginName;
    private String email;
 
    /**
     * @param imageId
     * @param loginName
     * @param email
     */
    public RowAccount(int imageId, String loginName, String email) 
    {
        this.imageId = imageId;
        this.loginName = loginName;
        this.email = email;
    }
    /**
     * @return
     */
    public int getImageId() 
    {
        return imageId;
    }
    /**
     * @param imageId
     */
    public void setImageId(int imageId) 
    {
        this.imageId = imageId;
    }
    /**
     * @return
     */
    public String getLoginName() 
    {
        return loginName;
    }
    /**
     * @param loginName
     */
    public void setLoginName(String loginName) 
    {
        this.loginName = loginName;
    }
    /**
     * @return
     */
    public String getEmail() 
    {
        return email;
    }
    /**
     * @param email
     */
    public void setEmail(String email) 
    {
        this.email = email;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() 
    {
        return loginName + "\n" + email;
    }
}
