package com.motm.models;

import java.util.Date;

public class Item
{
    
    private String loginName; 
    private Date date;
    private String name;
    private String location;
    private String reward;
    private String type;
    private String category;
    private String description;
    
    Item(String loginName, Date date, String name, String location, String reward, String type, String category, String description)
    {
        this.loginName = loginName;
        this.date = date;
        this.name = name;
        this.location = location;
        this.reward = reward;
        this.type = type;
        this.category = category;
        this.description = description;
    }
    
    
    /**
     * @return the username
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName the login name to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the reward
     */
    public String getReward() {
        return reward;
    }

    /**
     * @param reward the reward to set
     */
    public void setReward(String reward) {
        this.reward = reward;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
