package com.motm.adapters;

import java.util.List;
import com.motm.R;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.motm.helpers.Logger;
import com.motm.models.Item;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemViewAdapter extends ArrayAdapter<Item> implements Filterable
{
    Context context;
    int layoutResourceId;
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
        this.layoutResourceId = resourceId;
        this.items = items;
        this.originalItems = items;
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
    }

    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(item.getName());
        holder.descriptionView.setText(item.getDescription());
        holder.typeView.setText(item.getType().toString());
        holder.itemIdView.setText(item.getItemID().toString());
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
                int split = constraint.toString().indexOf('|');
                String sortBy = constraint.subSequence(0, split).toString();
                String search = constraint.subSequence(split+1, constraint.length()).toString();
                
                List<Item> filteredResults = new ArrayList<Item>();

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                if(search == null || search.length() == 0){
                    // set the Original result to return  
                    results.count = originalItems.size();
                    results.values = originalItems;
                } else {
                    
                    search = search.toString().toLowerCase();
                    for(int i = 0; i < originalItems.size(); i++){
                        Item data = originalItems.get(i);
                        
                        // compare by the sortBy
                        if(sortBy.equals("Name")){
                            // name
                            if(data.getName().toLowerCase().contains(search)){
                                filteredResults.add(data);
                            }
                            
                        } else if(sortBy.equals("Category")){
                            // category
                            if(data.getCategory().toLowerCase().contains(search)){
                                filteredResults.add(data);
                            }
                            
                        } else if(sortBy.equals("Status")){
                            // status
                            if(data.getStatus().toString().toLowerCase().contains(search)){
                                filteredResults.add(data);
                            }
                            
                        } else if(sortBy.equals("Date")){
                            // date
                            Date date = data.getDate();
                            date.setYear(date.getYear()-1900);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String dateSearch = formatter.format(date);
                            Logger.d("Date: "+dateSearch);
                            if(dateSearch.contains(search)){
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