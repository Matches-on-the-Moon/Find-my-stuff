package Adapters;

public class RowItem
{
    private int imageId;
    private String name;
    private String description;
    private String type;
    private Integer itemId;
 
    public RowItem(int imageId, String name, String description, String type, Integer itemId) 
    {
        this.imageId = imageId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.itemId = itemId;
    }
    public int getImageId() 
    {
        return imageId;
    }
    public void setImageId(int imageId) 
    {
        this.imageId = imageId;
    }
    public String getName() 
    {
        return name;
    }
    public void setName(String name) 
    {
        this.name = name;
    }
    public String getDescription() 
    {
        return description;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
}

