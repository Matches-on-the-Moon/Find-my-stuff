package com.motm.models;

import java.util.Date;

public class Item
{
    
    private String username; 
    private Date date;
    private String name;
    private String location;
    private String reward;
    private String type;
    private String category;
    private String description;
    
    Item(String loginName, Date addDate, String itemName, String itemLocation, String itemReward, String itemType, String itemCategory, String itemDescription)
    {
        username = loginName;
        date = addDate;
        name = itemName;
        location = itemLocation;
        reward = itemReward;
        type = itemType;
        category = itemCategory;
        description = itemDescription;
    }
    
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
