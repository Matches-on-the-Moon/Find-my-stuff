package com.motm.models;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public enum Status {
        Open, Resolved;
    }
    
    public enum Type {
        Lost, Found;
        
        @Override
        public String toString()
        {
            return this.name();
        }
    }
    
    private Integer id;
    private Integer ownerID; 
    private Date date;
    private String name;
    private String location;
    private Status status;
    private String reward;
    private Type type;
    private String category;
    private String description;
    
    Item(Integer id, Integer ownerID, String name, String location, Status status, String reward, Type type, String category, String description, Date date)
    {
        this.id = id;
        this.ownerID = ownerID;
        this.date = date;
        this.name = name;
        this.location = location;
        this.status = status;
        this.reward = reward;
        this.type = type;
        this.category = category;
        this.description = description;
    }
    
    public Integer getItemID()
    {
        return id;
    }
    
    public void setItemID(Integer id)
    {
        this.id = id;
    }
    
    /**
     * @return the ownerID
     */
    public Integer getOwnerID() {
        return ownerID;
    }

    /**
     * @param ownerID the account id to set
     */
    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
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
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
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
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
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
