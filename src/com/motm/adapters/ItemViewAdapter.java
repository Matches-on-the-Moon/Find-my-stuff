package com.motm.adapters;

import java.util.List;
import com.motm.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.motm.activities.FindItemActivity;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.helpers.Logger;
import com.motm.models.Item;
import com.motm.models.Item.Type;
import com.motm.models.interfaces.ItemManager;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ItemViewAdapter extends ArrayAdapter<Item> implements Filterable
{
    Context context;
    int resourceId;
    List<Item> items = null;
    List<Item> originalItems = null;

    /**
     * @param context
     * @param resourceId
     * @param items
     */
    public ItemViewAdapter(Context context, int resourceId, List<Item> items)
    {
        super(context, resourceId, items);
        this.context = context;
        this.resourceId = resourceId;
        this.items = items;
        originalItems = items;
    }

    /**
     *
     */
    private class ViewHolder
    {
        ImageView imageView;
        TextView nameView;
        TextView descriptionView;
        TextView typeView;
        TextView itemIdView;
        Button matchesButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;

        Item item = items.get(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_find_list_rows, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.descriptionView = (TextView) convertView.findViewById(R.id.description);
            holder.typeView = (TextView) convertView.findViewById(R.id.type);
            holder.itemIdView = (TextView) convertView.findViewById(R.id.itemId);
            holder.imageView = (ImageView) convertView.findViewById(R.id.itemImage);
            holder.matchesButton = (Button) convertView.findViewById(R.id.matchesButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(item.getName());
        holder.descriptionView.setText(item.getDescription());
        holder.typeView.setText((item.getType()==null)?"":item.getType().toString());
        Integer id = item.getItemID();
        holder.itemIdView.setText((id==null)?"":id.toString());
        
        if(item.getType()==Type.LOST) {
        	
	        ItemManager im = Factory.getItemManager();
	        List<Item> matches = im.getMatches(item);
	        if( matches != null && item.getOwnerID() == FMSApplication.getInstance().getCurrentAccount().getAccountId()){
	        	int numMatches = matches.size();
	        	holder.matchesButton.setText(numMatches+((numMatches==1)?" match":" matches"));
	        	holder.matchesButton.setVisibility(View.VISIBLE);
	        	FindItemActivity.addButton(holder.matchesButton, id);
	        	
	        } else {
	        	holder.matchesButton.setVisibility(View.INVISIBLE);
	        }
	        
        } else {
        	holder.matchesButton.setVisibility(View.INVISIBLE);
        }
        holder.imageView.setImageResource(R.drawable.question_mark); //rowItem.getImageId()

        return convertView;
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                items = (List<Item>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                
                String sortBy = "";
                String search = "";
                String location = null;
                        
                String [] tokens = constraint.toString().split("\\|");
                //Logger.d("constraint: "+constraint.toString()+" len: "+tokens.length);
                
                if(tokens.length==1){
                	sortBy = tokens[0];
                	search="";
                }
                if(tokens.length==2){
                	sortBy = tokens[0];
                	search = tokens[1];
                }if(tokens.length==3){//Name|name|Location|		location is blank
                	sortBy = "Found";
                	search = tokens[1];
                	location = "";
                	//Logger.d("Search *"+search+"* @"+location+"@");
                }if(tokens.length==4){//Name|name|Location|location
                	sortBy = "Found";
                	search = tokens[1];
                	location = tokens[3];
                	//Logger.d("Search *"+search+"* @"+location+"@");
                }
                
                Logger.d("sortBy: "+sortBy);
                
                List<Item> filteredResults = new ArrayList<Item>();

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                if(search == null || (search.length() == 0 && !sortBy.equals("Found"))){
                    // set the Original result to return  
                    results.count = originalItems.size();
                    results.values = originalItems;
                } else {
                    
                    search = search.toLowerCase(Locale.ENGLISH);
                    Item data;
                    for(int i = 0; i < originalItems.size(); i++){
                        data = originalItems.get(i);
                        
                        // compare by the sortBy
                        if(sortBy.equals("Name")){
                            // name
                            if(data.getName().toLowerCase(Locale.ENGLISH).contains(search)){
                                filteredResults.add(data);
                            }
                            
                        } else if(sortBy.equals("Category")){
                            // category
                            if(data.getCategory().toLowerCase(Locale.ENGLISH).contains(search)){
                                filteredResults.add(data);
                            }
                            
                        } else if(sortBy.equals("Status")){
                            // status
                            if(data.getStatus().toString().toLowerCase(Locale.ENGLISH).contains(search)){
                                filteredResults.add(data);
                            }
                            
                        } else if(sortBy.equals("Date")){
                        	if (search.length() == 10) {
	                            if(data.getCalendar().after(new GregorianCalendar(Integer.parseInt(search.substring(0,4)), Integer.parseInt(search.substring(5, 7)), Integer.parseInt(search.substring(8,10))))) {
	                                filteredResults.add(data);
	                            }
                        	}
                        }else if(sortBy.equals("Found")){
                        	
                        	if( data.getType()==Type.FOUND && data.getName().toLowerCase(Locale.ENGLISH).contains(search) &&
                        			data.getLocation().toLowerCase(Locale.ENGLISH).contains(location) ){
                        		
                        		filteredResults.add(data);
                        	}
                        }
                    }
                    // set the Filtered result to return
                    results.count = filteredResults.size();
                    results.values = filteredResults;
                }
                
                return results;
            }
        };
    }
    
    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Item getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return items.get(position).getItemID();
    }
}