package com.motm.adapters;

import java.util.List;
import com.motm.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.motm.models.Item;
 
public class ItemViewAdapter extends ArrayAdapter<Item>
{ 
    Context context;
 
    public ItemViewAdapter(Context context, int resourceId, List<Item> items)
    {
        super(context, resourceId, items);
        this.context = context;
    }
 
    private class ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView descriptionView;
        TextView typeView;
        TextView itemIdView;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        Item item = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
        	convertView = mInflater.inflate(R.layout.item_find_list_rows, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.descriptionView = (TextView) convertView.findViewById(R.id.description);
            holder.typeView = (TextView) convertView.findViewById(R.id.type);
            holder.itemIdView = (TextView) convertView.findViewById(R.id.itemId);
            holder.imageView = (ImageView) convertView.findViewById(R.id.itemImage);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        	holder.nameView.setText(item.getName()); 
        	holder.descriptionView.setText(item.getDescription()); 
        	holder.typeView.setText(item.getType().toString());
        	holder.itemIdView.setText(item.getItemID().toString());
        	holder.imageView.setImageResource(R.drawable.question_mark); //rowItem.getImageId()
        	return convertView;
    }
}